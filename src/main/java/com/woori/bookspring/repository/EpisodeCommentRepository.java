package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.EpisodeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeCommentRepository extends JpaRepository<EpisodeComment, Long> {
    List<EpisodeComment> findByUserId(Long userId);
}
