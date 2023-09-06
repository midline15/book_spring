package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.InventoryEbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryEbookRepository extends JpaRepository<InventoryEbook, Long> {
}
