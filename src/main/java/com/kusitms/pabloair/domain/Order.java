package com.kusitms.pabloair.domain;

import com.kusitms.pabloair.config.BooleanConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Convert(converter = BooleanConverter.class)
    private Boolean orderStatus;

    private LocalDateTime orderFinTime;

    //연관관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getOrderList().add(this);
    }

    public Order(String s, LocalDateTime now, boolean b, User user) {
        this.orderTime = now;
        this.orderStatus = b;
        this.setUser(user);
    }

}
