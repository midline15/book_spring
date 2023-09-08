package com.woori.bookspring.dto;

import com.woori.bookspring.entity.ebook.Episode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EpisodeFormDto {

    private String title;
    private String content;

    public Episode toEntity() {
        return Episode.builder()
                .title(title)
                .content(content).build();
    }
}
