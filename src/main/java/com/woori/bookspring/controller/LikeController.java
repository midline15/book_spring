package com.woori.bookspring.controller;

import com.woori.bookspring.dto.EbookDto;
import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequestMapping("like")
@RequiredArgsConstructor
@Controller
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public String showLike(Model model, Principal principal){
        List<EbookDto> ebookList = likeService.getLikeEbookList(principal.getName());
        model.addAttribute("list", ebookList);
        return "ebook/like";
    }

    @PostMapping("ebook/{ebook-id}")
    public String addLikeEbook(@PathVariable("ebook-id") Long ebookId, Principal principal){
        likeService.addLikeEbook(ebookId, principal.getName());

        return "redirect:/ebook/"+ebookId;
    }
}
