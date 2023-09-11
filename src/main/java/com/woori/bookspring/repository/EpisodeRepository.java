package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
