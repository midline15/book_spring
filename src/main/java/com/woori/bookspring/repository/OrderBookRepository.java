package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Order;
import com.woori.bookspring.entity.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    List<OrderBook> findByOrder(Order order);
}
