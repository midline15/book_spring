package com.woori.bookspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartBookDto {

    private Long id;
    private Long bookId;
    private int count;
    private String title;
    private int price;

    public OrderBookDto toOrderBook() {
        return OrderBookDto.builder()
                .bookId(bookId)
                .count(count)
                .price(price*count)
                .build();
    }
}
