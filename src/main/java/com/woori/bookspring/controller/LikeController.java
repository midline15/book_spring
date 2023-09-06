package com.woori.bookspring.controller;

import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.entity.ebook.LikeEbook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("like")
@RequiredArgsConstructor
@Controller
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public String showLike(Model model, User user){
        Like like = likeService.getLike(user);
        List<LikeEbook> likeEbookList = likeService.getLikeEbookList(like);
        model.addAttribute("list", likeEbookList);
        return "ebook/like";
    }
}
