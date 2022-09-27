package com.kusitms.pabloair.domain;

import com.kusitms.pabloair.config.BooleanConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Convert(converter = BooleanConverter.class)
    private Boolean orderStatus;    //TRUE, FALSE

    private LocalDateTime orderFinTime;

    private String storeName;

    private String serialNumber;


    //연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
        user.getOrderList().add(this);
    }

   public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
   }

   //생성 메서드
    public static Order createOrder(User user, String storeName, List<OrderItem> orderItems, String serialNumber) {
        Order order = new Order();
        order.setUser(user);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderTime(LocalDateTime.now());
        order.setOrderStatus(false);
        order.setStoreName(storeName);
        order.setSerialNumber(serialNumber);

        return order;
    }

    //전체 주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
