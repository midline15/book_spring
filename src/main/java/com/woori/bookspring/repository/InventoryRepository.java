package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByUser_Email(String email);
}
