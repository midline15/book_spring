package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
