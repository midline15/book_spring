package com.woori.bookspring.repository;

import com.woori.bookspring.entity.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartBookRepository extends JpaRepository<CartBook, Long> {
}
