package com.woori.bookspring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderBookDto {

    private Long bookId;
    private int count;
    private int price;
    private int totalPrice;
    public int getTotalPrice(){
        return count*price;
    }

}
