package com.woori.bookspring.dto;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Cover;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookFormDto {

    private Long id;

    @NotBlank(message = "책 제목을 입력 해주세요.")
    private String title;

    @NotBlank(message = "책 소개를 입력 해주세요.")
    private String intro;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;

    private float avgScore;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private int stockNumber; // 재고

    @NotBlank(message = "출판사를 입력 해주세요.")
    private String publisher; // 출판사

    private LocalDate origDate; // 초판 발행일

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;


    private Long coverId;

    private String url;
    private List<BookCommentDto> bookCommentDtoList;
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
