package com.bingbong.jpabook.jpashop.api;

import com.bingbong.jpabook.jpashop.domain.Order;
import com.bingbong.jpabook.jpashop.respository.OrderRepository;
import com.bingbong.jpabook.jpashop.respository.OrderSearch;
import com.bingbong.jpabook.jpashop.respository.order.simplequery.OrderSimpleQueryDto;
import com.bingbong.jpabook.jpashop.respository.order.simplequery.OrderSimpleQueryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // Order 엔티티를 반환하고 있다. 잭슨 라이브러리가 Order안의 Member, Member안의 Order를 찾느라고 무한 루프에 빠진다.
    // 엔티티를 반환하려면 @JsonIgnore를 통해서 해당 필드를 무시하도록 해야한다.
    // @JsonIgnore를 추가해도 에러가 난다.
    // 왜냐면 Order의 Member가 LAZY이기 때문. 프록시 객체(ByteBuddyInterceptor 클래스)를 반환한다.
    // 잭슨 라이브러리가 프록시 객체를 보고 어떻게 할 수가 없어서 에러가 난다.
    // Hibernate5Module 의존성을 넣어서 해결가능하긴 하지만 이렇게 하지말고 DTO를 넣으면 해결된다.
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // 단순히 조회를 해서 DB에 쿼리가 나가도록 해서 LAZY 강제 초기화
            order.getDelivery().getAddress(); // LAZY 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> ordersV2() {
        // ORDER 2개
        // 1 + N 문제. 현재 ORDER를 가져오는 쿼리 1개 하고 N개를 가져옴. 여기서는 2개를 가져옴
        // 회원 N 번과 배송 N번을 가져옴.
        // 정확히는 처음 ORDER 가져오는 쿼리 1개 -> 결과로 2개가 나옴 -> 회원 쿼리 1개 -> 배달 쿼리 1개 -> 다시 회원 쿼리 1개 -> 다시 배달 쿼리 1개
        // 지연로딩은 영속성 컨텍스트에서 조회하므로 이미 있는 값이라면 DB에 쿼리를 날리지 않고 있는 걸 가져오기 때문에 N번이 일어나지 않을 수 있다. (주문의 MEMBER_ID를 같게 하면 조회할 때 멤버는 한 번만 조회한다.)
        // EAGER를 한다고 해서 해결되지 않는다.
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream()
            .map(o -> new OrderSimpleQueryDto(o.getId(), o.getMember().getName(), o.getOrderDate(), o.getStatus(), o.getDelivery().getAddress()))
            .collect(Collectors.toList());
    }

    // join fetch를 통해서 다 긁어올 수 있다. 그러나 조회하는 칼럼이 너무나도 많다.
    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        return orders.stream()
            .map(o -> new OrderSimpleQueryDto(o.getId(), o.getMember().getName(), o.getOrderDate(), o.getStatus(), o.getDelivery().getAddress()))
            .collect(Collectors.toList());
    }

    // 직접 쿼리를 작성했기 때문에 원하는 칼럼만 가져올 수 있다.
    // 그러나 프레젠테이션 레이어에 쓰이는 게 Repository 레이어까지 왔다.
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }
}
