package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Order;
import com.woori.bookspring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Username(String username);
}
