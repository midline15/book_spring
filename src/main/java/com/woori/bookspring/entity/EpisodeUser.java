package com.woori.bookspring.entity;

import com.woori.bookspring.dto.EpisodeUserDto;
import com.woori.bookspring.entity.base.BaseEntity;
import com.woori.bookspring.entity.ebook.Episode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class EpisodeUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static EpisodeUser createEpisodeUser(Episode episode, User user) {
        return EpisodeUser.builder()
                .episode(episode)
                .user(user)
                .build();
    }

    public EpisodeUserDto of() {
        return EpisodeUserDto.builder()
                .id(id)
                .userId(user.getId())
                .episodeId(episode.getId())
                .build();
    }
}
