package com.woori.bookspring.entity;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.entity.base.BaseBook;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}