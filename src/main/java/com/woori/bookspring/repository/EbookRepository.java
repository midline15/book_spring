package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Ebook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EbookRepository extends JpaRepository<Ebook, Long> {
    Page<Ebook> findByTitleContaining(Pageable pageable, String searchValue);
    Page<Ebook> findByIntroContaining(Pageable pageable, String searchValue);

}
