package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Like;
import com.woori.bookspring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUser(User user);
}
