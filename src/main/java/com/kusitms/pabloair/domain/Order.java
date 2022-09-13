package com.kusitms.pabloair.domain;

import com.kusitms.pabloair.BooleanConverter;
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

    @Column(name = "order_sn", nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Convert(converter = BooleanConverter.class)
    private Boolean orderStatus;

    private LocalDateTime orderFinTime;


    public Order(String s, LocalDateTime now, boolean b) {
        this.serialNumber = s;
        this.orderTime = now;
        this.orderStatus = b;
    }

}
