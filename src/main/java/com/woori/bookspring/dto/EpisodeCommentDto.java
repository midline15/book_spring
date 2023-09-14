package com.woori.bookspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeCommentDto {
    private Long id;
    private String email;
    private String ebookTitle;
    private String episodeTitle;
    private LocalDateTime regTime;
    private int score;
}
