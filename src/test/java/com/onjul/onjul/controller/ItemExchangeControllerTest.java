package com.onjul.onjul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onjul.onjul.constant.ItemCategory;
import com.onjul.onjul.constant.ItemSellStatus;
import com.onjul.onjul.dto.ItemDto;
import com.onjul.onjul.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
