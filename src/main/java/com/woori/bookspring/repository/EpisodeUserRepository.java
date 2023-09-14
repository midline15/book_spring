package com.woori.bookspring.repository;

import com.woori.bookspring.entity.EpisodeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeUserRepository extends JpaRepository<EpisodeUser, Long> {
    List<EpisodeUser> findByUser_Email(String email);
}
