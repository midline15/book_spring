package com.woori.bookspring.dto;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Cover;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookFormDto {

    private Long id;
    private String title;
    private String intro;
    private int price;

    private int stockNumber; // 재고

    private String publisher; // 출판사

    private LocalDate origDate; // 초판 발행일

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;

    public Book toEntity(Cover cover){
        return Book.builder()
                .title(title)
                .intro(intro)
                .cover(cover)
                .price(price)
                .stockNumber(stockNumber)
                .publisher(publisher)
                .origDate(origDate)
                .sellStatus(sellStatus)
                .build();
    }
}
