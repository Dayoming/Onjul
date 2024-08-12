package com.onjul.onjul.dto;

import com.onjul.onjul.constant.ItemCategory;
import com.onjul.onjul.constant.ItemSellStatus;
import com.onjul.onjul.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ItemDto {
    private Long id; // 상품 코드
    private String itemNm; // 상품명
    private ItemCategory itemCategory; // 상품 카테고리
    private String sellerId; // 파는 사람 아이디
    private String sellerNm; // 파는 사람 별명
    private Integer price; // 가격
    private Integer stockNumber; // 수량
    private String itemDetail; // 상품 상세 설명
    private ItemSellStatus itemSellStatus; // 상품 판매 상태
    private LocalDateTime regTime; // 등록 시간
    private LocalDateTime updateTime; // 수정 시간

    public ItemDto(Item entity) {
        this.id = entity.getId();
        this.itemNm = entity.getItemNm();
        this.itemCategory = entity.getItemCategory();
        this.sellerNm = entity.getSellerNm();
        this.price = entity.getPrice();
        this.stockNumber = entity.getStockNumber();
        this.itemSellStatus = entity.getItemSellStatus();
    }

    public Item toEntity() {
        return new Item(id, itemNm, itemCategory, sellerId, sellerNm, price,
                stockNumber, itemDetail, itemSellStatus, regTime, updateTime);
    }
}
