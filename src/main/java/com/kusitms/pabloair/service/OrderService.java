package com.kusitms.pabloair.service;

import com.kusitms.pabloair.domain.Item;
import com.kusitms.pabloair.domain.Order;
import com.kusitms.pabloair.domain.OrderItem;
import com.kusitms.pabloair.domain.User;
import com.kusitms.pabloair.dto.OrderRequestDto;
import com.kusitms.pabloair.dto.OrderResponseDto;
import com.kusitms.pabloair.repository.ItemRepository;
import com.kusitms.pabloair.repository.OrderRepository;
import com.kusitms.pabloair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    public Long order(Long userId, String storeName, List<OrderRequestDto> orders) {
        User user = userRepository.findById(userId).get();

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderRequestDto order : orders) {
            Item item = itemRepository.findById(order.getId()).get();
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), order.getCount());
            orderItems.add(orderItem);
        }

        //주문 생성
        Order order = Order.createOrder(user, storeName, orderItems);
        orderRepository.save(order);

        return order.getId();
    }

    //주문 조회
    public List<OrderResponseDto> getOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponseDto> collect = orders.stream().map(m -> new OrderResponseDto(m.getId(), m.getOrderStatus(), m.getStoreName()))
                .collect(Collectors.toList());

        return collect;
    }
}
