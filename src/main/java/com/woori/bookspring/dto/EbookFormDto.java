package com.woori.bookspring.dto;

import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.entity.ebook.Ebook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbookFormDto {

    private Long id;
    private String title;
    private String intro;
    private int price;
    private Long coverId;
    private String url;
    private Cover cover;


    public Ebook toEntity(Cover cover){
        return Ebook.builder()
                .title(title)
                .intro(intro)
                .cover(cover)
                .price(price)
                .avgScore(1)
                .build();
    }
}