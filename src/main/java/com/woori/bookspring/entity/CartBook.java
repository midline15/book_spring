package com.woori.bookspring.entity;

import com.woori.bookspring.dto.CartBookDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class CartBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public static CartBook createCartBook(Book book, Cart cart, int count) {
        return CartBook.builder()
                .book(book)
                .cart(cart)
                .count(count)
                .build();
    }


    // 장바구니에 담을 수량 증가
    public void addCount(int count){
        this.count += count;
    }

    // 장바구니에 담겨있는 수량 변경
    public void updateCount(int count){
        this.count = count;
    }

    public CartBookDto of() {
        return CartBookDto.builder()
                .id(id)
                .bookId(book.getId())
                .count(count)
                .title(book.getTitle())
                .price(book.getPrice())
                .build();
    }
}