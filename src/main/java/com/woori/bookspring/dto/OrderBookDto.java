package com.woori.bookspring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderBookDto {

    private Long bookId;
    private String title;
    private int count;
    private int totalPrice;
}
