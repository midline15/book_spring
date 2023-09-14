package com.woori.bookspring.entity.base;

import com.woori.bookspring.constant.Genre;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@MappedSuperclass
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseBook extends BaseEntity {

    protected String title;
    protected List<Genre> genreList;
    protected String intro; // 소개글
    protected int price;
    @Builder.Default
    protected float avgScore = 0; // 평균 별점
    protected int totalSales; // 총 판매량
}
