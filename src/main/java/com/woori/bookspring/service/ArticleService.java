package com.woori.bookspring.service;

import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    //게시물 작성
    @Transactional
    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    //게시물 조회
    public void getArticle(Long id) {
        articleRepository.findById(id).get();
    }

    //게시물 전체 조회
    public List<Article> getArticleList(){
        return articleRepository.findAll();
    }

    //게시물 수정
    @Transactional
    public void updateArticle(Article article) {
        articleRepository.save(article);
    }

    //게시물 삭제
    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
