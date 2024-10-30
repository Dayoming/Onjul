package com.tiffy.controller;

import com.tiffy.constant.ItemSellStatus;
import com.tiffy.dto.ItemDto;
import com.tiffy.service.ItemService;
import com.tiffy.entity.Item;
import com.tiffy.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/exchange")
public class ItemExchangeController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @GetMapping("/new")
    public String newExchangeItemForm(Model model) {
        model.addAttribute("itemDto", new ItemDto());
        return "exchange/exchange-new";
    }

    @PostMapping("/createItem")
    public String createExchangeItem(@Valid ItemDto itemDto) {
        itemDto.setItemSellStatus(ItemSellStatus.SELL);

        LocalDateTime localDateTime = LocalDateTime.now();

        itemDto.setRegTime(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        itemDto.setUpdateTime(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
        return "redirect:/exchange/";
    }

    @GetMapping("/")
    public String exchangeList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<ItemDto> itemPages = itemService.paging(pageable);

        int blockLimit = 10; // page 개수 설정
        int currentPage = pageable.getPageNumber();

        // 페이지가 비어있는 경우 처리
        if (itemPages.getTotalPages() == 0) {
            model.addAttribute("itemList", itemPages);
            model.addAttribute("startPage", 1);
            model.addAttribute("endPage", 1);  // 1 페이지만 표시
            return "exchange/exchange-list";
        }

        int startPage = (((int) Math.ceil(((double) currentPage / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), itemPages.getTotalPages());

        model.addAttribute("itemList", itemPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "exchange/exchange-list";
    }

    @GetMapping("/{id}")
    public String exchangeListDetail(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id).orElse(null);
        model.addAttribute("item", item);
        return "exchange/exchange-detail";
    }

    @GetMapping("/delete/{id}")
    public String exchangeListDelete(@PathVariable Long id) {
        Item target = itemRepository.findById(id).orElse(null);
        if (target != null)
            itemRepository.deleteById(id);
        return "redirect:/exchange/";
    }

    @GetMapping("/edit/{id}")
    public String exchangeListEdit(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id).orElse(null);
        model.addAttribute("item", item);
        return "exchange/exchange-edit";
    }

    @PostMapping("/update")
    public String exchangeListUpdate(ItemDto itemDto) {
        LocalDateTime localDateTime = LocalDateTime.now();
        itemDto.setUpdateTime(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 임시 sellerNm, sellerId
        itemDto.setSellerNm("Admin");
        itemDto.setSellerId("Admin");

        Item item = itemDto.toEntity();
        Item target = itemRepository.findById(item.getId()).orElse(null);
        if (target != null) {
            itemRepository.save(item);
        }
        return "redirect:/exchange/" + item.getId();
    }


}
