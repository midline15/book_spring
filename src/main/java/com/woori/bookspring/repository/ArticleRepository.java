package com.woori.bookspring.repository;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.entity.board.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByArticleType(ArticleType articleType);

    List<Article> findByUserIdAndArticleType(Long userId, ArticleType articleType);
}
