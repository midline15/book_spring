package com.woori.bookspring.repository;

import com.woori.bookspring.entity.board.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByUserId(Pageable pageable, Long userId);
}
