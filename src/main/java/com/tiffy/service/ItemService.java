package com.tiffy.service;

import com.tiffy.dto.ItemDto;
import com.tiffy.entity.Item;
import com.tiffy.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<ItemDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // page 위치에 있는 값 0부터 시작
        int pageLimit = 10; // 한 페이지에 보여줄 글 개수

        Page<Item> itemPages = itemRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // 목록 : id, itemNm, itemCategory, sellerNm, price, stockNumber, itemSellStatus
        Page<ItemDto> itemDtos = itemPages.map(itemPage -> new ItemDto(itemPage));
        
        return itemDtos;
    }

}
