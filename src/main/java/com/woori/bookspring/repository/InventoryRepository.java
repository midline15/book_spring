package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
