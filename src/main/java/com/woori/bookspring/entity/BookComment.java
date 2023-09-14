package com.woori.bookspring.entity;

import com.woori.bookspring.dto.BookCommentDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class BookComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int score; // 별점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookCommentDto of() {
        return BookCommentDto.builder()
                .id(id)
                .email(user.getEmail())
                .bookTitle(book.getTitle())
                .regTime(getRegTime())
                .score(score)
                .build();
    }
}
