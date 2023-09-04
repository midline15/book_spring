package com.woori.bookspring.repository;

import com.woori.bookspring.entity.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
}
