package com.woori.bookspring.repository;

import com.woori.bookspring.entity.board.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
