package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EbookRepository extends JpaRepository<Ebook, Long> {
}
