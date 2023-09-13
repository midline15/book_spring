package com.woori.bookspring.dto;

import com.woori.bookspring.constant.PermitStatus;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Episode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeFormDto {

    private String title;
    private String content;
    private Long id;
    private PermitStatus permitStatus;
    private Ebook ebook;

    public Episode toEntity() {
        return Episode.builder()
                .title(title)
                .content(content).build();
    }
}
