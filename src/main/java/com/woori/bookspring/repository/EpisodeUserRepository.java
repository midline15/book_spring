package com.woori.bookspring.repository;

import com.woori.bookspring.entity.EpisodeUser;
import com.woori.bookspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeUserRepository extends JpaRepository<EpisodeUser, Long> {
    List<EpisodeUser> findByUser_EmailAndEpisode_EbookId(String email, Long ebookId);

    Optional<EpisodeUser> findByEpisodeIdAndUser_Email(Long id, String email);
}
