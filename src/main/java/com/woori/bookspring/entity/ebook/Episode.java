package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.constant.PermitStatus;
import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.entity.EpisodeUser;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    private int totalScore;

    @Enumerated(EnumType.STRING)
    private PermitStatus permitStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ebook_id")
    private Ebook ebook;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EpisodeUser> episodeUserList;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EpisodeComment> episodeCommentList;

    public static Episode createEpisode(EpisodeFormDto episodeFormDto, Ebook ebook) {
        return Episode.builder()
                .title(episodeFormDto.getTitle())
                .content(episodeFormDto.getContent())
                .ebook(ebook)
                .build();
    }

    public EpisodeFormDto of() {
        return EpisodeFormDto.builder()
                .id(id)
                .ebookId(ebook.getId())
                .ebookTitle(ebook.getTitle())
                .title(title)
                .content(content)
                .episodeCommentDtoList(episodeCommentList.stream().map(EpisodeComment::of).toList())
                .regTime(getRegTime())
                .writerNickname(ebook.getUser().getNickname())
                .build();
    }

    public void updateEpisode(EpisodeFormDto episodeFormDto) {
        title = episodeFormDto.getTitle();
        content = episodeFormDto.getContent();
    }

    public void addScore(int score) {
        totalScore += score;
        ebook.calculateAvgScore();
    }
}
