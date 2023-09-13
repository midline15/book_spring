package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.constant.PermitStatus;
import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Episode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private PermitStatus permitStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ebook_id")
    private Ebook ebook;

    public static Episode createEpisode(EpisodeFormDto episodeFormDto, Ebook ebook) {
        return Episode.builder()
                .title(episodeFormDto.getTitle())
                .content(episodeFormDto.getContent())
                .ebook(ebook)
                .build();
    }
}
