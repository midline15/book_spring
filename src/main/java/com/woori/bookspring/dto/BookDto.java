package com.woori.bookspring.dto;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.entity.Cover;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private Long id; //아이디
    private int price;  //가격
    private String intro;  //소개
    private String title;  //제목
    private SellStatus sellStatus;  // 재고
    private String url; // 유알엘
}
