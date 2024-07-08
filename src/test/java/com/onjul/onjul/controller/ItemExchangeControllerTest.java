package com.onjul.onjul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onjul.onjul.constant.ItemCategory;
import com.onjul.onjul.constant.ItemSellStatus;
import com.onjul.onjul.dto.ItemDto;
import com.onjul.onjul.entity.Item;
import com.onjul.onjul.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

@WebMvcTest(ItemExchangeController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemExchangeControllerTest {

    @Autowired
    ItemExchangeController itemExchangeController;

    private MockMvc mock;
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ItemRepository itemRepository;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule()); // JavaTimeModule 등록
        mock = MockMvcBuilders.standaloneSetup(itemExchangeController).build();
    }

    @Test
    @DisplayName("교환 게시판 상품 입력 테스트")
    public void createItemTest() throws Exception {
        ItemDto itemDto = new ItemDto(null, "테스트 상품", ItemCategory.ETC, "admin", "admin",
                10000, 2, "테스트 상품 상세 설명", ItemSellStatus.SELL, LocalDateTime.now(), LocalDateTime.now());

        mock.perform(MockMvcRequestBuilders.post("/exchange/createItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/exchange/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("교환 게시판 특정 상품 조회 테스트")
    public void ItemTest() throws Exception {
        ItemDto itemDto = new ItemDto(null, "테스트 상품", ItemCategory.ETC, "admin", "admin",
                10000, 2, "테스트 상품 상세 설명", ItemSellStatus.SELL, LocalDateTime.now(), LocalDateTime.now());
        Item item = itemDto.toEntity();
        itemRepository.save(item);

        // ItemRepository의 동작을 모킹
        Mockito.when(itemRepository.findById(0L)).thenReturn(Optional.of(item));

        // GET 요청 수행 및 검증
        mock.perform(MockMvcRequestBuilders.get("/exchange/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("item"))
                .andExpect(MockMvcResultMatchers.model().attribute("item", item));
    }
}
