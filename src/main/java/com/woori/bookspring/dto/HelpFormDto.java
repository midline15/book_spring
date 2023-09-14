package com.woori.bookspring.dto;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelpFormDto {

    private Long id;
    private String title;
    private String content;
    private String articleType;

    public Article toEntity(User user) {
        return Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .articleType(ArticleType.valueOf(articleType))
                .build();
    }
}
