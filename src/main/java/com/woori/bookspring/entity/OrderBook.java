package com.woori.bookspring.entity;

import com.woori.bookspring.dto.OrderBookDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class OrderBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    public OrderBookDto of(){
        return OrderBookDto.builder()
                .bookId(book.getId())
                .price(totalPrice)
                .count(count)
                .build();
    }

    public static OrderBook createOrderBook(OrderBookDto orderBookDto, Book book, Order order){
        return OrderBook.builder()
                .count(orderBookDto.getCount())
                .totalPrice(orderBookDto.getTotalPrice())
                .book(book)
                .order(order) // 주문상품에 주문 정보를 연결
                .build();
    }
}