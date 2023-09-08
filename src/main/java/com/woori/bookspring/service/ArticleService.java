package com.woori.bookspring.service;

import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public void getArticle(Long id) {
        articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Article> getArticleList(){
        return articleRepository.findAll();
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
