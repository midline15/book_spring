package com.woori.bookspring.service;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.IntStream;

@Getter
public class PaginationService {
    private static final int BAR_LENGTH = 5;
    private int startNumber;
    private int endNumber;
    private int currentPage;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {

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



    public static void pagination(Model model, Page<?> list, int page, String searchType, String searchValue){
        PaginationService paging = new PaginationService();
        model.addAttribute("list", list);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, list.getTotalPages()));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
    }
}

