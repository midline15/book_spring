package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.entity.ebook.LikeEbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeEbookRepository extends JpaRepository<LikeEbook, Long> {
    List<LikeEbook> findByLikeId(Long likeId);
}
