package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.entity.ebook.LikeEbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LikeEbookRepository extends JpaRepository<LikeEbook, Long> {
    List<LikeEbook> findByLikes(Like like);


    Optional<LikeEbook> findByEbookIdAndLikes(Long ebookId, Like like);
}
