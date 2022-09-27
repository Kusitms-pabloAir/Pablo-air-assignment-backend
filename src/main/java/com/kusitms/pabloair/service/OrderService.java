package com.kusitms.pabloair.service;

import com.kusitms.pabloair.config.AES256Util;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final AES256Util aes256Util;
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

        //주문별 고유 시리얼넘버 생성
        String serialNumber = createSerialNumber();

        //주문 생성
        Order order = Order.createOrder(user, storeName, orderItems, serialNumber);
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

    //주문별 시리얼넘버 생성
    public String createSerialNumber() {
        //고유 시리얼넘부 생성하기
        String serialNumber = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar dateTime = Calendar.getInstance();
        serialNumber = sdf.format(dateTime.getTime());

        //yyyymmddhh24missSSS_랜덤문자6개
        serialNumber = serialNumber + "_" + RandomStringUtils.randomAlphanumeric(6);

        // 암호화
        // String encode = passwordEncoder.encode(serialNumber);

        //AES256 사용 암호화
        String encode;

        try {
            encode = aes256Util.encrypt(serialNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return encode;
    }

    //주문별 시리얼 넘버 조회
    public String getSerialNumber(Long orderId) {
        return orderRepository.getSerialNumber(orderId);
    }

    //검증
    public boolean validateSerialNumber(String serialNumber) {
        Order order = orderRepository.validateSerialNumber(serialNumber);
        System.out.println(">>>>>>>>>>>>>>>>>serialNumber>>>>>>>" + serialNumber);
        System.out.println(">>>>>>>>>>>>orderId>>>>>>>>>>>>" + order.getId());
        if (order == null) {
            return false;
        }
        else {
            orderRepository.update(order.getId());
            return true;
        }
    }
}
