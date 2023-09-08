package com.woori.bookspring.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartOrderDto {

    private List<CartBookDto> cartBookDtoList;
}
