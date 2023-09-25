package com.woori.bookspring.repository;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findByRole(Pageable pageable, Role role);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
