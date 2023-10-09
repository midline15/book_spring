package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_Email(Pageable pageable, String email);
}
