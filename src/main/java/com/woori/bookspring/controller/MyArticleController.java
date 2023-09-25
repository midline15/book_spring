package com.woori.bookspring.controller;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.*;
import com.woori.bookspring.service.ArticleService;
import com.woori.bookspring.service.BookCommentService;
import com.woori.bookspring.service.CommentService;
import com.woori.bookspring.service.EpisodeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MyArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final BookCommentService bookCommentService;
    private final EpisodeCommentService episodeCommentService;


    @GetMapping("user/{user-id}/article/{article-type}")
    public String manageMyArticle(@PageableDefault(sort = "regTime", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("user-id") Long userId, @PathVariable("article-type") String articleType, Model model){
        Page<ArticleDto> articleList = articleService.getUserArticleList(pageable, userId, ArticleType.getArticleType(articleType));
        ArticleListDto articleListDto = new ArticleListDto(articleType, articleList);
        model.addAttribute("list", articleListDto);
        return "user/myArticle";
    }

    @GetMapping("user/{user-id}/comment")
    public String manageMyComment(@PathVariable("user-id") Long userId,Model model){
        List<CommentDto> commentList = commentService.getCommentList(userId);
        model.addAttribute("list",commentList);
        return "user/myComment";
    }

    @GetMapping("user/{user-id}/book-comment")
    public String manageMyBookComment(@PathVariable("user-id") Long userId,Model model){
        List<BookCommentDto> commentList = bookCommentService.getBookCommentList(userId);
        model.addAttribute("list",commentList);
        return "user/myComment";
    }

    @GetMapping("user/{user-id}/episode-comment")
    public String manageMyEpisodeComment(@PathVariable("user-id") Long userId,Model model){
        List<EpisodeCommentDto> commentList = episodeCommentService.getEpisodeCommentList(userId);
        model.addAttribute("list",commentList);
        return "user/myComment";
    }
}
