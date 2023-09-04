package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.LikeEBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeEBookRepository extends JpaRepository<LikeEBook, Long> {
}
