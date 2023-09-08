package com.woori.bookspring.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDto {
    private Long id;
    private List<CartBookDto> cartBookDtoList;
}
