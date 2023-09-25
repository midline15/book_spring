package com.woori.bookspring.repository;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.entity.board.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByArticleType(Pageable pageable, ArticleType articleType);

    Page<Article> findByUserIdAndArticleType(Pageable pageable, Long userId, ArticleType articleType);

    Page<Article> findByArticleTypeAndTitleContains(Pageable pageable, ArticleType articleType, String searchValue);

    Page<Article> findByArticleTypeAndContentContains(Pageable pageable, ArticleType articleType, String searchValue);

    Page<Article> findByArticleTypeAndUser_NicknameContains(Pageable pageable, ArticleType articleType, String searchValue);
}
