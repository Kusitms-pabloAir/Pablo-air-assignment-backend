package com.kusitms.pabloair.repository;

import com.kusitms.pabloair.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    @Query(value = "select o.serial_number from orders o where o.order_id = :orderId", nativeQuery = true)
    String getSerialNumber(@Param("orderId") Long orderId);

    @Query(value = "select * from orders o where o.serial_number = :sn", nativeQuery = true)
    Order validateSerialNumber(@Param("sn") String serialNumber);

    @Transactional
    @Modifying
    @Query(value = "update orders o set o.order_status = 'True', o.order_fin_time = now() " +
            "where o.order_id = :orderId and o.order_status = 'False'", nativeQuery = true)
    void update(@Param("orderId") Long id);
}
