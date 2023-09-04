package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.EBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EBookRepository extends JpaRepository<EBook, Long> {
}
