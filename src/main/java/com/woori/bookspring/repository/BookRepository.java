package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContaining(String searchValue, Pageable pageable);
    Page<Book> findByPublisherContaining(String searchValue, Pageable pageable);

    Page<Book> findByWriterContaining(String searchValue, Pageable pageable);
}
