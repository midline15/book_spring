package com.woori.bookspring.dto;

import com.woori.bookspring.constant.ArticleType;
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

    public Article toEntity(User user) {
        return Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .articleType(ArticleType.ARTICLE)
                .user(user)
                .build();
    }
}
