package com.tiffy.entity;

import com.tiffy.constant.ItemSellStatus;
import com.tiffy.constant.ItemCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory; // 상품 카테고리

    @Column(nullable = false)
    private String sellerId; // 파는 사람 아이디

    @Column(nullable = false)
    private String sellerNm; // 파는 사람 별명

    @Column(name="price", nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 등록 시간

    private LocalDateTime updateTime; // 수정 시간

    @Builder
    public Item(String itemNm, ItemCategory itemCategory, String sellerId, String sellerNm, int price, int stockNumber, String itemDetail) {
        this.itemNm = itemNm;
        this.itemCategory = itemCategory;
        this.sellerId = sellerId;
        this.sellerNm = sellerNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = ItemSellStatus.SELL;
        this.regTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
