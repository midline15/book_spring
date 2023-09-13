package com.woori.bookspring.repository;

import com.woori.bookspring.dto.BookDto;
import com.woori.bookspring.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String searchValue);
    List<Book> findByPublisherContaining(String searchValue);


}
