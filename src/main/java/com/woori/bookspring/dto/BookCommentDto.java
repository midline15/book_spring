package com.woori.bookspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookCommentDto {
    private Long id; // BookComment의 ID

    private String content; // 코멘트 내용

    private int score; // 별점

    private String email; // 사용자 ID

    private Long bookId; // 책 ID

    private String bookTitle;

    private LocalDateTime regTime;
}
