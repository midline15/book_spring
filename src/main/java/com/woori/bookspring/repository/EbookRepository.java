package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.ebook.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EbookRepository extends JpaRepository<Ebook, Long> {
    List<Ebook> findByTitleContaining(String searchValue);
    List<Ebook> findByIntroContaining(String searchValue);
}
