package com.woori.bookspring.service;

import com.woori.bookspring.entity.board.Article;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.IntStream;

@Getter
public class PaginationService {

    private static final int BAR_LENGTH = 5;
    private int startNumber;
    private int endNumber;
    private int currentPage;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        //int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 1);
        //int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages+1);
        currentPage=currentPageNumber;
        endNumber = totalPages;
        int mod = totalPages % BAR_LENGTH;
        if(totalPages - mod >= currentPageNumber) {
            endNumber = (int) (Math.ceil((float)currentPageNumber / BAR_LENGTH) * BAR_LENGTH);
            startNumber = endNumber - 4;
        } else {
            startNumber = (int) (Math.ceil((float)currentPageNumber / BAR_LENGTH) * BAR_LENGTH) - 4;
        }

        return IntStream.range(startNumber, endNumber+1).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
/*

    @GetMapping({"/", "", "lists"})
    public String getArticlePage(Model model,
                                 @PageableDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(required = false) String searchType,
                                 @RequestParam(required = false) String searchValue) {

        Page<Article> articleList = articleService.getArticleList(searchType, searchValue, pageable.withPage(page-1));

        int totalPage = articleList.getTotalPages();

        PaginationService paging = new PaginationService();

        model.addAttribute("list",articleList);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, totalPage));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        return "view";
    }*/


    /*@Transactional(readOnly = true)
    public Page<Article> getArticleList(String searchType, String searchValue, Pageable pageable) {
        *//*if (searchType != null) {
            switch (searchType) {
                case "제목":
                    return articleRepository.findByTitleContains(searchValue,pageable);
                case "본문":
                    return articleRepository.findByContentContains(searchValue,pageable);
                case "id":
                    return articleRepository.findByUser_UserIdContains(searchValue,pageable);
                case "닉네임":
                    return articleRepository.findByUser_NicknameContains(searchValue,pageable);
            }
        }*//*

        return articleRepository.findAll(pageable);
    }*/
