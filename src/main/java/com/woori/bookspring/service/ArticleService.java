package com.woori.bookspring.service;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.*;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.board.Comment;
import com.woori.bookspring.repository.ArticleRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public Long createArticle(ArticleFormDto articleFormDto, String email) {
        String error = "dlrpajsh";
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Article article = articleFormDto.toEntity(user);
        return articleRepository.save(article).getId();
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new).of();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> getArticleList(Pageable pageable, ArticleType articleType, String searchType, String searchValue) {

        Page<Article> articleList;
        if (searchType != null && searchType.equals("content")){
            articleList = articleRepository.findByArticleTypeAndContentContains(pageable, articleType, searchValue);
        } else if (searchType != null && searchType.equals("nickname")) {
            articleList = articleRepository.findByArticleTypeAndUser_NicknameContains(pageable, articleType, searchValue);
        } else if(searchType != null && searchType.equals("title")){
            articleList = articleRepository.findByArticleTypeAndTitleContains(pageable, articleType, searchValue);
        }else{
            articleList = articleRepository.findByArticleType(pageable, articleType);
        }

        return articleList.map(Article::of);
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> getUserArticleList(Pageable pageable, Long userId, ArticleType articleType) {
        return articleRepository.findByUserIdAndArticleType(pageable, userId, articleType).map(Article::of);
    }

    public void updateArticle(ArticleDto articleDto, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        article.updateArticle(articleDto);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}

