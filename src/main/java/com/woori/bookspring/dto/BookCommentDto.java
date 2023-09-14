package com.woori.bookspring.dto;


import com.woori.bookspring.entity.BookComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookCommentDto {
    private Long id; // BookComment의 ID

    private String content; // 코멘트 내용

    private int score; // 별점

    private Long userId; // 사용자 ID

    private Long bookId; // 책 ID
}
