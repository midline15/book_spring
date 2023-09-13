package com.woori.bookspring.dto;

import com.woori.bookspring.constant.SellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ArticleDto {

    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime regTime;
}
