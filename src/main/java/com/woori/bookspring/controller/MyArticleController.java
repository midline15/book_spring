package com.woori.bookspring.controller;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.*;
import com.woori.bookspring.service.*;
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
    public String manageMyArticle(Model model,
                                  @PageableDefault(sort = "regTime", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(defaultValue = "1") int page,
                                  @PathVariable("user-id") Long userId,
                                  @PathVariable("article-type") String articleType,
                                  @RequestParam(required = false) String searchType,
                                  @RequestParam(required = false) String searchValue) {

        Page<ArticleDto> articleList = articleService.getUserArticleList(pageable, userId, ArticleType.getArticleType(articleType));

        int totalPage = articleList.getTotalPages();
        ArticleListDto articleListDto = new ArticleListDto(articleType, articleList, totalPage);

        PaginationService paging = new PaginationService();

        model.addAttribute("list", articleListDto);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, totalPage));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("list", articleListDto);
        return "user/myArticle";
    }

    @GetMapping("user/{user-id}/comment")
    public String manageMyComment(@PathVariable("user-id") Long userId, Model model) {
        List<CommentDto> commentList = commentService.getCommentList(userId);
        model.addAttribute("list", commentList);
        return "user/myComment";
    }

    @GetMapping("user/{user-id}/book-comment")
    public String manageMyBookComment(@PathVariable("user-id") Long userId, Model model) {
        List<BookCommentDto> commentList = bookCommentService.getBookCommentList(userId);
        model.addAttribute("list", commentList);
        return "user/myComment";
    }

    @GetMapping("user/{user-id}/episode-comment")
    public String manageMyEpisodeComment(@PathVariable("user-id") Long userId, Model model) {
        List<EpisodeCommentDto> commentList = episodeCommentService.getEpisodeCommentList(userId);
        model.addAttribute("list", commentList);
        return "user/myComment";
    }
}
