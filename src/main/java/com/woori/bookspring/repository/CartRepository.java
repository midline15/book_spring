package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Cart;
import com.woori.bookspring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);
}
