package com.woori.bookspring.controller;

import com.woori.bookspring.dto.InventoryEbookDto;
import com.woori.bookspring.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequestMapping("/inven")
@RequiredArgsConstructor
@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public String showInventory(Model model, Principal principal){
        List<InventoryEbookDto> inventoryEbookDtoList = inventoryService.getInventoryEbookList(principal.getName());
        model.addAttribute("list", inventoryEbookDtoList);
        return "ebook/inven";
    }
}
