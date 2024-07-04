package com.onjul.onjul.repository;

import com.onjul.onjul.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
