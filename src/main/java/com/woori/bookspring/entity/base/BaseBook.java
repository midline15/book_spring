package com.woori.bookspring.entity.base;

import com.woori.bookspring.constant.Genre;
import lombok.Getter;

import java.util.List;

@Getter
public class BaseBook {

    protected String title;
    protected List<Genre> genreList;
    protected String intro; // 소개글
    protected int price;
    protected int avgScore; // 평균 별점
    protected int totalSales; // 총 판매량
}
