package com.woori.bookspring.controller;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("like")
@RequiredArgsConstructor
@Controller
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public String showLike(Model model, Principal principal){
        List<EbookFormDto> ebookList = likeService.getLikeEbookList(principal.getName());
        model.addAttribute("list", ebookList);
        return "user/like";
    }

    @PostMapping("ebook/{ebook-id}")
    public String addLikeEbook(@PathVariable("ebook-id") Long ebookId, Principal principal){
        likeService.createLikeEbook(ebookId, principal.getName());

        return "redirect:/ebook/"+ebookId;
    }

    @DeleteMapping("ebook/{ebook-id}")
    public ResponseEntity<?> deleteLikeEbook(@PathVariable("ebook-id") Long likeEbookId){
        likeService.deleteLikeEbook(likeEbookId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
