package com.woori.bookspring.dto;

import com.woori.bookspring.constant.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private int orderPrice;
    private LocalDateTime regTime;
    private List<OrderBookDto> orderBookDtoList;
    private OrderStatus orderStatus;

}
