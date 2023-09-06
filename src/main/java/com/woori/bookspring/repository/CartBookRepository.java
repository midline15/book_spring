package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Cart;
import com.woori.bookspring.entity.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartBookRepository extends JpaRepository<CartBook, Long> {
    void deleteByCartId(Long cartId);

    List<CartBook> findByCart(Cart cart);
}
