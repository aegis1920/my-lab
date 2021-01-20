package com.bingbong.jpabook.jpashop.service.query;

import com.bingbong.jpabook.jpashop.domain.Order;
import com.bingbong.jpabook.jpashop.respository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDto> ordersV3() {
        List<Order> all = orderRepository.findAllWithItem();

        return all.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

}
