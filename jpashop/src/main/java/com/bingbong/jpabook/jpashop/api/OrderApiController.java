package com.bingbong.jpabook.jpashop.api;

import com.bingbong.jpabook.jpashop.domain.Address;
import com.bingbong.jpabook.jpashop.domain.Order;
import com.bingbong.jpabook.jpashop.domain.OrderItem;
import com.bingbong.jpabook.jpashop.domain.OrderStatus;
import com.bingbong.jpabook.jpashop.respository.OrderRepository;
import com.bingbong.jpabook.jpashop.respository.OrderSearch;
import com.bingbong.jpabook.jpashop.respository.order.query.OrderFlatDto;
import com.bingbong.jpabook.jpashop.respository.order.query.OrderItemQueryDto;
import com.bingbong.jpabook.jpashop.respository.order.query.OrderQueryDto;
import com.bingbong.jpabook.jpashop.respository.order.query.OrderQueryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    // OrderSimpleApiController에 있는 버전 1과 같음. 엔티티를 직접 노출하기 때문에 안 씀
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            order.getOrderItems()
                .forEach(o -> o.getItem().getName());
        }
        return all;
    }

    // 속에 있는 얘들까지 다 DTO로 바꾸자. Address같은 VO는 상관없음
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        return all.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    // order_item에 ORDER_ID가 4인게 2개가 있다. 그래서 조인하게 되면 ORDER가 2개로 뻥튀기 되어버린다.
    // DB입장에서 조인하면 총 4개가 나오게된다. ORDER_ITEM이 11인 게 2개, 4인 게 2개이기 때문에 ORDER도 4개가 된다.
    // 쿼리에 distinct를 넣어서 해결해줄 수 있다.
    // DB입장에서는 distinct를 넣으나 안 넣으나 똑같다. 똑같이 4개를 가져온다. 근데 JPA에서 자체적으로 Order가 같은 ID값이면 알아서 중복을 제거한다.
    // 그래서 우리가 원하는대로 데이터를 가져오는 것. 한 방 쿼리로 가져온다.
    // 한방 쿼리로 가져올 수 있으나 중복데이터가 너무 많다. 이 데이터 전송률이 많다.
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> all = orderRepository.findAllWithItem();

        return all.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    // DB 쿼리를 보면 limit이 없다. 애초에 DB 조회가 뻥튀기 돼서 오기 때문에 paging이 안된다. 그래서 그대로 가지고 와서 메모리에서 하려고하는데
    // 잘못하면 아웃오브메모리가 난다.
    // 게다가 필드가 List로 2개 이상을 조인한다면 뻥튀기에 또 뻥튀기가 돼서 정합성 자체가 안맞을 수 있다. 하나만 페치 조인하자
    @GetMapping("/api/v3/orders-paging")
    public List<OrderDto> ordersV3Paging() {
        List<Order> all = orderRepository.findAllWithItemByPaging();

        return all.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    // ToOne은 페치조인, 그냥 일대다 관계에 있는 얘(order_item)는 LAZY로 가져온다.
    // 배치 사이즈를 정해주면 1 * 1 * 1이 나온다.
    // 쿼리가 3번 나간다. Order, orderItem, item
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3SolvePaging(
        @RequestParam(value = "offset", defaultValue = "0") int offset,
        @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        List<Order> all = orderRepository.findAllWithMemberDelivery(offset, limit);

        return all.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    // 쿼리는 루트 1번, 컬렉션은 N번 실행. ToOne은 조인해도 증가안함. 다른 건 증가함.
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDtoOptimization();
    }

    // 이렇게 해도 join을 했기 때문에 중복이 들어가게 된다. 즉, 뻥튀기 된다.
    // 장점을 쿼리가 한방으로 나갔다는 것. Order를 기준으로 페이징도 안됨. orderItems 기준으로는 가능
    // 그리고 스펙이 달라서 메모리, 즉 어플리케이션 단에서 바꿔줘야 한다.
    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDtoFlat();

        return flats.stream()
            .collect(Collectors.groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                    o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                Collectors.mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                    o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())

            )).entrySet().stream()
            .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                e.getKey().getAddress(), e.getValue()))
            .collect(Collectors.toList());
    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
