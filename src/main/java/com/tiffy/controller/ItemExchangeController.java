package com.tiffy.controller;

import com.tiffy.constant.ItemSellStatus;
import com.tiffy.dto.ItemDto;
import com.tiffy.entity.User;
import com.tiffy.repository.UserRepository;
import com.tiffy.service.ItemService;
import com.tiffy.entity.Item;
import com.tiffy.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    UserRepository userRepository;

    @Autowired
    ItemService itemService;

    // 로그인한 사용자의 아이디를 가져오는 메서드
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "Anonymous";  // 인증되지 않은 경우 기본값 반환
        }

        Object principal = authentication.getPrincipal();
        String userId = null;

        if (principal instanceof UserDetails) {
            userId = ((UserDetails) principal).getUsername();
        } else {
            userId = principal.toString();
        }

        return userId;
    }

    // 로그인한 사용자의 닉네임을 가져오는 메서드
    public String getCurrentUserNickname() {
        // 사용자 정보 조회 (DB에서 username을 통해 User 엔티티를 가져옴)
        User user = userRepository.findByUsername(getCurrentUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getNickname();  // 닉네임 반환
    }

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

        itemDto.setSellerNm(getCurrentUserNickname());
        itemDto.setSellerId(getCurrentUserId());

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

        // 현재 로그인한 사용자와 작성자가 같은지 확인
        boolean isOwner = item != null && getCurrentUserId() != null &&
                getCurrentUserId().equals(item.getSellerId());

        model.addAttribute("isOwner", isOwner); // 소유자 여부를 모델에 추가
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
        itemDto.setSellerNm(getCurrentUserNickname());
        itemDto.setSellerId(getCurrentUserId());

        Item item = itemDto.toEntity();
        Item target = itemRepository.findById(item.getId()).orElse(null);
        if (target != null) {
            itemRepository.save(item);
        }
        return "redirect:/exchange/" + item.getId();
    }


}
