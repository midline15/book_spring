package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Inventory;
import com.woori.bookspring.entity.ebook.InventoryEbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryEbookRepository extends JpaRepository<InventoryEbook, Long> {
    List<InventoryEbook> findByInventory(Inventory inventory);
}
