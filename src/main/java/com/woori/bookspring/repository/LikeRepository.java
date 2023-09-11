package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUser_Username(String username);
}
