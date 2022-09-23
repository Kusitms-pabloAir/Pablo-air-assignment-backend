package com.kusitms.pabloair.repository;

import com.kusitms.pabloair.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface ValidateRepository extends JpaRepository<Order, Long> {

    /*
    @Query(value = "select * from orders o, user u " +
            "where o.user_id = o.user_id " +
            "and o.order_status = 'False' " +
            "and u.serial_number = :sn " +
            "and o.user_id = :userId"
          , nativeQuery = true)
    Order validate(@Param("userId") Long userId, @Param("sn") String serialNumber);
     */

    @Transactional
    @Modifying
    @Query(value = "update orders o set o.order_status = 'True', o.order_fin_time = now() " +
            "where o.user_id = :id and o.order_status = 'False'", nativeQuery = true)
    void update(@Param("id") Long id);

}
