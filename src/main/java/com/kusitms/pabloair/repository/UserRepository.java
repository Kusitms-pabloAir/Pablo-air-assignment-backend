package com.kusitms.pabloair.repository;

import com.kusitms.pabloair.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
