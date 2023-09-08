package com.woori.bookspring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EbookDto {
    private Long ebookId;
    private String title;
}
