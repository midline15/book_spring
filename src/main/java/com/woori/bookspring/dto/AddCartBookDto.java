package com.woori.bookspring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartBookDto {
    private Long bookId;
    private int count;
}
