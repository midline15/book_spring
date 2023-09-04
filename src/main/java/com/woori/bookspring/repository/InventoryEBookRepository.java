package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.InventoryEBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryEBookRepository extends JpaRepository<InventoryEBook, Long> {
}
