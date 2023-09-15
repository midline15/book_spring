package com.woori.bookspring.controller;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.ArticleDto;
import com.woori.bookspring.dto.ArticleFormDto;
import com.woori.bookspring.dto.CommentDto;
import com.woori.bookspring.service.ArticleService;
import com.woori.bookspring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
  
    @GetMapping("article/{article-type}")
    public String getNoticeList(Model model, @PathVariable("article-type") String articleType){
        List<ArticleDto> articleList = articleService.getArticleList(ArticleType.getArticleType(articleType));
        ArticleListDto articleListDto = new ArticleListDto(articleType, articleList);

        model.addAttribute("list", articleListDto);
        if (articleType.equals("01") || articleType.equals("02")) {
            return "board/board";
        }
        return "help/board";
    }

    @GetMapping("/article/{article-type}/new") //글쓰기 폼
    public String newArticle(@PathVariable("article-type") String articleType, Model model) {
        model.addAttribute("dto", new ArticleFormDto(articleType));
        if (articleType.equals("01") || articleType.equals("02")) {
            return "board/articleForm";
        }
        return "help/articleForm";
    }

    @PostMapping("article/{article-type}") //글쓰기
    public String createArticle(ArticleDto dto, Principal principal) {
        Long articleId = articleService.createArticle(dto, principal.getName());//principal.getName => email
        return "redirect:/article/{article-type}/"+articleId;

    }

    @GetMapping("article/{article-type}/{article-id}") //게시판 단건 조회
    public String getArticle(@PathVariable("article-type") String articleType,@PathVariable("article-id") Long articleId, Model model) {
        ArticleDto articleDto = articleService.getArticle(articleId);
        articleDto.setArticleType(articleType);
        model.addAttribute("article", articleDto);
        if (articleType.equals("01") || articleType.equals("02")) {
            return "board/article";
        }
        return "help/article";
    }

    @GetMapping("article/{article-type}/{article-id}/update") //수정 폼
    public String updateArticle(@PathVariable("article-type") String articleType, @PathVariable("article-id") Long articleId, Model model) {
        ArticleDto articledto = articleService.getArticle(articleId);
        model.addAttribute("dto", articledto);
        if (articleType.equals("01") || articleType.equals("02")) {
            return "board/articleForm";
        }
        return "help/articleForm";
    }

    @PatchMapping("article/{article-type}/{article-id}") // 수정
    public ResponseEntity<?> updateArticle(@PathVariable("article-id") Long articleId, @RequestBody ArticleDto articleDto) {
        System.out.println(articleDto.getContent());
        articleService.updateArticle(articleDto, articleId);
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("article/{article-type}/{article-id}") //삭제
    public @ResponseBody String deleteArticle(@PathVariable("article-id") Long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/article/{article-type}";
    }

    @PostMapping("article/{article-type}/{article-id}/comment") //댓글 달기
    public String createArticleComment(@PathVariable("article-id") Long articleId,
                                       CommentDto commentDto) {
        commentService.createComment(articleId, commentDto);
        return "redirect:/article/{article-type}/{article-id}";
    }

    @PatchMapping("article/{article-type}/{article-id}/comment/{comment-id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto){
        System.out.println(commentDto.getContent());
        commentService.updateComment(commentDto);
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("article/{article-type}/{article-id}/comment/{comment-id}")
    public ResponseEntity<?> deleteComment(@PathVariable("comment-id") Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("댓글 삭제", HttpStatus.OK);
    }
}
