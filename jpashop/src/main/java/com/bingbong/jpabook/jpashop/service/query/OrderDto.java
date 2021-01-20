package com.bingbong.jpabook.jpashop.service.query;

import com.bingbong.jpabook.jpashop.domain.Address;
import com.bingbong.jpabook.jpashop.domain.Order;
import com.bingbong.jpabook.jpashop.domain.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class OrderDto {

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
