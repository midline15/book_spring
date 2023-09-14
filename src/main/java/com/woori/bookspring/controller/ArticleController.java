package com.woori.bookspring.controller;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.ArticleDto;
import com.woori.bookspring.dto.ArticleFormDto;
import com.woori.bookspring.dto.CommentDto;
import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.board.Comment;
import com.woori.bookspring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("article")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("01") //자유게시판
    public String getArticleList(Model model) {
        List<ArticleDto> articleList = articleService.getArticleList(ArticleType.ARTICLE);
        model.addAttribute("list", articleList);
        return "board/board";
    }

    @GetMapping("01/new") //글쓰기 폼
    public String newArticle(Model model) {
        model.addAttribute("dto", new Article());
        return "board/articleForm";
    }

    @PostMapping("01") //글쓰기
    public String createArticle(ArticleFormDto dto, Principal principal) {
        articleService.createArticle(dto, principal.getName()); //principal.getName => email
        return "redirect:/article/01";

    }

    @GetMapping("01/{article-id}") //게시판 단건 조회
    public String getArticle(@PathVariable("article-id") Long articleId, Model model) {
        ArticleDto articleDto = articleService.getArticle(articleId);
        model.addAttribute("article", articleDto);

        return "board/article";
    }

    @DeleteMapping("01/{article-id}") //삭제
    public @ResponseBody String deleteArticle(@PathVariable("article-id") Long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/article/01";
    }

    @GetMapping("01/{article-id}/form") //수정 폼
    public String updateArticle(@PathVariable("article-id") Long articleId, Model model) {
        ArticleDto articledto = articleService.getArticle(articleId);
        model.addAttribute("dto", articledto);
        return "board/articleUpdateForm";
    }

    @PostMapping("01/{article-id}") // 수정
    public @ResponseBody String updateArticle(@PathVariable("article-id") Long articleId, ArticleDto articleDto) {
        articleService.updateArticle(articleDto, articleId);
        return "redirect:/article/01";
    }

    @PostMapping("01/{article-id}/articleComment") //댓글 달기
    public String createArticleComment(@PathVariable("article-id") Long articleId,
                                       CommentDto commentDto,
                                       Principal principal) {
        articleService.createArticleComment(articleId, commentDto, principal);
        return "redirect:/article/01";
    }

    @GetMapping("02") //리뷰게시판
    public String getReviewList(Model model) {
        List<ArticleDto> articleList = articleService.getArticleList(ArticleType.REVIEW);
        model.addAttribute("list", articleList);
        return "board/board";
    }

    @PostMapping("02") //리뷰 게시판 글쓰기
    public String createReview(ArticleFormDto dto, Principal principal) {
        articleService.createArticle(dto, principal.getName()); //principal.getName => email
        return "redirect:/article/02";

    }
}
