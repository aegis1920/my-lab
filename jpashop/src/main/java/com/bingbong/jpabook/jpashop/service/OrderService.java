package com.bingbong.jpabook.jpashop.service;

import com.bingbong.jpabook.jpashop.domain.Delivery;
import com.bingbong.jpabook.jpashop.domain.Member;
import com.bingbong.jpabook.jpashop.domain.Order;
import com.bingbong.jpabook.jpashop.domain.OrderItem;
import com.bingbong.jpabook.jpashop.domain.item.Item;
import com.bingbong.jpabook.jpashop.respository.ItemRepository;
import com.bingbong.jpabook.jpashop.respository.MemberRepository;
import com.bingbong.jpabook.jpashop.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

//    public List<Order> findOrders
}
