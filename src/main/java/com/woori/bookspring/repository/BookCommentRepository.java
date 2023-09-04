package com.woori.bookspring.repository;

import com.woori.bookspring.entity.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}
