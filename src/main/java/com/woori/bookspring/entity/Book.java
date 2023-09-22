package com.woori.bookspring.entity;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.dto.BookDto;
import com.woori.bookspring.dto.BookFormDto;
import com.woori.bookspring.dto.CartBookDto;
import com.woori.bookspring.entity.base.BaseBook;
import com.woori.bookspring.exception.OutOfStockException;
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

    public BookFormDto of() {
        return BookFormDto.builder()
                .id(id)
                .title(title)
                .price(price)
                .intro(intro)
                .publisher(publisher)
                .origDate(origDate)
                .sellStatus(sellStatus)
                .stockNumber(stockNumber)
                .url(cover.getUrl())
                .coverId(cover.getId())
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

    public void updateBook(BookFormDto bookFormDto, Cover cover) {
        this.cover = cover;
        this.title = bookFormDto.getTitle();
        this.intro = bookFormDto.getIntro();
        this.price = bookFormDto.getPrice();
        this.publisher = bookFormDto.getPublisher();
        this.sellStatus = bookFormDto.getSellStatus();
        this.stockNumber = bookFormDto.getStockNumber();
    }

    public void sellBook(int count) {
        if(stockNumber == count){
            this.sellStatus = SellStatus.SOLD_OUT;
        } else if (stockNumber < count) {
            throw new OutOfStockException(stockNumber);
        }
        this.stockNumber -= count;
        this.totalSales += count;
    }
}