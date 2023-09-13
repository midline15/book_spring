package com.woori.bookspring.dto;

import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleFormDto {

    private Long id;
    private String title;
    private String content;
    private User userId;

    public Article toEntity() {
        return Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .user(userId)
                .build();
    }
}
