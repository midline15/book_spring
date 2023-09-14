package com.woori.bookspring.service;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.entity.ebook.InventoryEbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InventoryServiceTest {

    @Autowired
    InventoryService inventoryService;

    @Test
    @DisplayName("인벤토리에 이북 추가")
    public void createInventoryEbookTest(){
        EbookFormDto inventoryEbook = inventoryService.createInventoryEbook(Long.valueOf(1), "test@test");
        System.out.println(inventoryEbook.getTitle());
    }

}