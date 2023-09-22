package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.dto.EpisodeCommentDto;
import com.woori.bookspring.entity.User;
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
public class EpisodeComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int score; // 별점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episode episode;

    public static EpisodeComment createEpisodeComment(User user, Episode episode, EpisodeCommentDto episodeCommentDto) {
        return EpisodeComment.builder()
                .score(episodeCommentDto.getScore())
                .content(episodeCommentDto.getContent())
                .user(user)
                .episode(episode)
                .build();
    }

    public EpisodeCommentDto of() {
        return EpisodeCommentDto.builder()
                .id(id)
                .content(content)
                .score(score)
                .episodeTitle(episode.getTitle())
                .ebookTitle(episode.getEbook().getTitle())
                .email(user.getEmail())
                .regTime(getRegTime())
                .build();
    }
}
