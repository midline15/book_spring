package com.woori.bookspring.service;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    /*@Test
    @DisplayName("책 추가")
    public void createBookTest(){
        Book book = Book.builder()
                .title("테스트")
                .intro("테스트입니다")
                .avgScore(5)
                .price(10000)
                .sellStatus(SellStatus.SALE)
                .build();
        System.out.println(bookService.createBook(book).getTitle());
    }*/
}