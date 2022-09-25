package com.kusitms.pabloair.repository;

import com.kusitms.pabloair.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
