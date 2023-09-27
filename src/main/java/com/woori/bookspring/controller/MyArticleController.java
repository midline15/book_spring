package com.woori.bookspring.controller;

import com.woori.bookspring.config.auth.UserDetailsImpl;
import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.*;
import com.woori.bookspring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MyArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final BookCommentService bookCommentService;
    private final EpisodeCommentService episodeCommentService;


    @GetMapping("user/article/{article-type}")
    public String manageMyArticle(Model model, @AuthenticationPrincipal UserDetailsImpl principal,
                                  @PageableDefault(sort = "regTime", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(defaultValue = "1") int page,
                                  @PathVariable("article-type") String articleType,
                                  @RequestParam(required = false) String searchType,
                                  @RequestParam(required = false) String searchValue) {

        Page<ArticleDto> articleList = articleService.getUserArticleList(pageable.withPage(page-1), principal.getUser().getId(), ArticleType.getArticleType(articleType));
        model.addAttribute("articleType", articleType);
        PaginationService.pagination(model, articleList, page, searchType, searchValue);
        return "user/myArticle";
    }

    @GetMapping("user/{comment-type}")
    public String manageMyComment(Model model,
                                  @PathVariable("comment-type") String commentType,
                                  @AuthenticationPrincipal UserDetailsImpl principal,
                                  @PageableDefault(sort = "regTime", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(required = false) String searchType,
                                  @RequestParam(required = false) String searchValue) {
        Page<?> commentList;
        if(commentType.equals("comment")){
            commentList = commentService.getCommentList(pageable.withPage(page-1), principal.getUser().getId());
            model.addAttribute("commentType", "comment");
        } else if (commentType.equals("book-comment")){
            commentList = bookCommentService.getBookCommentList(pageable.withPage(page-1), principal.getUser().getId());
            model.addAttribute("commentType", "bookComment");
        } else {
            commentList = episodeCommentService.getEpisodeCommentList(pageable.withPage(page-1), principal.getUser().getId());
            model.addAttribute("commentType", "episodeComment");
        }
        PaginationService.pagination(model, commentList, page, searchType, searchValue);
        return "user/myComment";
    }

    @DeleteMapping("user/article/{article-type}")
    public ResponseEntity<?> deleteArticles(@RequestBody ArticleDeleteDto articleDeleteDto){
        articleDeleteDto.getArticleDtoList().forEach(articleDto -> articleService.deleteArticle(articleDto.getId()));
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
