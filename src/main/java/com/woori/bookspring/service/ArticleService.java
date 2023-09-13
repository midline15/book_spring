package com.woori.bookspring.service;

import com.woori.bookspring.dto.ArticleDto;
import com.woori.bookspring.dto.ArticleFormDto;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.repository.ArticleRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    public void createArticle(ArticleFormDto articleFormDto, User user){
        Article article = articleFormDto.toEntity();
        articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        return article.of();
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getArticleList() {
        return articleRepository.findAll().stream().map(Article::of).toList();
    }

    @Transactional
    public void updateArticle(Article article) {
        articleRepository.save(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
