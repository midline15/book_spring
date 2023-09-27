package com.woori.bookspring.repository;

import com.woori.bookspring.entity.BookComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    Page<BookComment> findByUserId(Pageable pageable, Long userId);
}
