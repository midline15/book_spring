package com.woori.bookspring.dto;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
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
public class ArticleDto {

    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime regTime;
    private String articleType;
    private List<CommentDto> commentDtoList;

    public Article toEntity(User user) {
        return Article.builder()
                .title(title)
                .content(content)
                .articleType(ArticleType.getArticleType(articleType))
                .user(user)
                .build();
    }
}
