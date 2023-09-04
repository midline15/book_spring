package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.EComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ECommentRepository extends JpaRepository<EComment, Long> {
}
