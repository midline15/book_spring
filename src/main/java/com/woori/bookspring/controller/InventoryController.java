package com.woori.bookspring.controller;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.dto.InventoryEbookDto;
import com.woori.bookspring.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/inven")
@RequiredArgsConstructor
@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public String showInventory(Model model, Principal principal){
        List<EbookFormDto> inventoryEbookDtoList = inventoryService.getInventoryEbookList(principal.getName());
        model.addAttribute("list", inventoryEbookDtoList);
        return "ebook/inven";
    }

    @PostMapping("ebook/{ebook-id}")
    public String addInventoryEbook(@PathVariable("ebook-id") Long ebookId, Principal principal){
        inventoryService.createInventoryEbook(ebookId, principal.getName());

        return "redirect:/ebook/"+ebookId;
    }

    @DeleteMapping("ebook/{ebook-id}")
    public ResponseEntity<?> deleteInventoryEbook(@PathVariable("ebook-id") Long inventoryEbookId){
        inventoryService.deleteInventoryEbook(inventoryEbookId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
