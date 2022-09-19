package com.kusitms.pabloair.repository;

import com.kusitms.pabloair.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query(value = "update user u set u.serial_number = :encode where user_id = :userId", nativeQuery = true)
    void saveWithSerialNumber(@Param(value = "userId") Long userId, @Param(value = "encode") String encode);
}
