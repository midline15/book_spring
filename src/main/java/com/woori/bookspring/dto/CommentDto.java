package com.woori.bookspring.dto;

import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.board.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private LocalDateTime regTime;
    private String content;
    private String email;

    public Comment toEntity(Article article, User user) {
        return Comment.builder()
                .id(id)
                .article(article)
                .content(content)
                .user(user)
                .build();
    }

}
