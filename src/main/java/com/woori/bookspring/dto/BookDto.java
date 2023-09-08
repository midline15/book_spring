package com.woori.bookspring.dto;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.entity.Cover;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private Long id;
    private int price;
    private String intro;
    private String title;
    private SellStatus sellStatus;
    private String url;
}
