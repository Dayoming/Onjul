package com.tiffy.repository;

import com.tiffy.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemNm(String itemNm);
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
    List<Item> findAllByOrderByPriceDesc();
    List<Item> findAllByOrderByPriceAsc();
}
