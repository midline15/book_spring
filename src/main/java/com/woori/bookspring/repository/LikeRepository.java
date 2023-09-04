package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
