package com.woori.bookspring.entity;

import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
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




    // --------------- 이부분 추가했는데 괜찮은지... ---------------------

    // 장바구니에 담을 상품 엔티티 생성
    public static CartBook createCartBook(Cart cart, Book book, int count){
        CartBook cartBook = new CartBook();
        cartBook.setCart(cart);
        cartBook.setBook(book);
        cartBook.setCount(count);
        return cartBook;
    }

    // 장바구니에 담을 수량 증가
    public void addCount(int count){
        this.count += count;
    }

    // 장바구니에 담겨있는 수량 변경
    public void updateCount(int count){
        this.count = count;
    }

}