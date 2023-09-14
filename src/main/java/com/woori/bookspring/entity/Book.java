package com.woori.bookspring.entity;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.dto.BookDto;
import com.woori.bookspring.entity.base.BaseBook;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Book extends BaseBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stockNumber; // 재고

    private String publisher; // 출판사

    private LocalDate origDate; // 초판 발행일

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private Cover cover;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookComment> bookCommentList;

    public BookDto of() {
        return BookDto.builder()
                .id(id)
                .title(title)
                .price(price)
                .intro(intro)
                .sellStatus(sellStatus)
                .url(cover.getUrl())
                .bookCommentDtoList(bookCommentList.stream().map(BookComment::of).toList())
                .avgScore(getAvgScore())
                .build();
    }

    public void calculateAvgScore() {
        int totalScore = 0;
        for (BookComment bookComment : bookCommentList){
            totalScore += bookComment.getScore();
        }
        avgScore = (float) totalScore/bookCommentList.size();
    }
}