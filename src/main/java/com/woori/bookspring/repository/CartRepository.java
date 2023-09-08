package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Cart;
import com.woori.bookspring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser_Username(String username);
}
