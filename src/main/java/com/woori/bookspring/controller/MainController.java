package com.woori.bookspring.controller;

import com.woori.bookspring.service.BookService;
import com.woori.bookspring.service.EbookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final BookService bookService;
    private final EbookService ebookService;

    @GetMapping
    public String mainPage(@PageableDefault(size = 3, sort = "totalSales",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("topBookList", bookService.getBookList(pageable,null,null));
        model.addAttribute("topEbookList", ebookService.getEbookList(pageable,null,null));
        return "index";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(RedirectAttributes redirectAttributes, HttpServletRequest request){
        System.out.println("권한");
        redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
