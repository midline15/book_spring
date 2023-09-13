package com.woori.bookspring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EbookDto {
    private Long ebookId; //아이디
    private String title; //제목
    private String url; //유알엘
    private String intro; // 소개
}
