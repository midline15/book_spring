package com.woori.bookspring.repository;

import com.woori.bookspring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
