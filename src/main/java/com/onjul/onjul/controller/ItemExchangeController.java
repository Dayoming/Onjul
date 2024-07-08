package com.onjul.onjul.controller;

import com.onjul.onjul.constant.ItemSellStatus;
import com.onjul.onjul.dto.ItemDto;
import com.onjul.onjul.entity.Item;
import com.onjul.onjul.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/exchange")
public class ItemExchangeController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/new")
    public String newExchangeItemForm() {
        return "exchange/exchange-new";
    }

    @PostMapping("/createItem")
    public String createExchangeItem(ItemDto itemDto) {
        itemDto.setItemSellStatus(ItemSellStatus.SELL);
        itemDto.setRegTime(LocalDateTime.now());
        itemDto.setUpdateTime(LocalDateTime.now());
        // 임시 sellerNm, sellerId
        itemDto.setSellerNm("Admin");
        itemDto.setSellerId("Admin");

        // price와 stockNumber가 null이면 기본값 설정
        if (itemDto.getPrice() == null) {
            itemDto.setPrice(0); // 기본값 설정
        }
        if (itemDto.getStockNumber() == null) {
            itemDto.setStockNumber(0); // 기본값 설정
        }

        Item item = itemDto.toEntity();
        Item saved = itemRepository.save(item);
        return "exchange/exchange-list";
    }
}
