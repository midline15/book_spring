package com.woori.bookspring.repository;

import com.woori.bookspring.entity.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
