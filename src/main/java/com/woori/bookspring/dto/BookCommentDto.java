package com.woori.bookspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCommentDto {

    private Long id;
    private String email;
    private String bookTitle;
    private LocalDateTime regTime;
    private int score;
}
