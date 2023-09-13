package com.woori.bookspring.controller;

import com.woori.bookspring.service.ArticleService;
import com.woori.bookspring.service.BookCommentService;
import com.woori.bookspring.service.CommentService;
import com.woori.bookspring.service.EpisodeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ArticleManageController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final BookCommentService bookCommentService;
    private final EpisodeCommentService episodeCommentService;


    /*@GetMapping("/user/{user-id}/article")
    public String manageMyArticle(@PathVariable("user-id") Long userId, @RequestParam String type){
        if(type.equals())
    }*/

}
