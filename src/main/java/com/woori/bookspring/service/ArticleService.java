package com.woori.bookspring.service;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.ArticleDto;
import com.woori.bookspring.dto.ArticleFormDto;
import com.woori.bookspring.dto.CommentDto;
import com.woori.bookspring.dto.HelpFormDto;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.board.Comment;
import com.woori.bookspring.repository.ArticleRepository;
import com.woori.bookspring.repository.UserRepository;
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
    private final UserRepository userRepository;

    public Long createArticle(ArticleDto articleDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Article article = articleDto.toEntity(user);
        return articleRepository.save(article).getId();
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new).of();
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getArticleList(ArticleType articleType) {
        return articleRepository.findByArticleType(articleType).stream().map(Article::of).toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getUserArticleList(Long userId, ArticleType articleType) {
        return articleRepository.findByUserIdAndArticleType(userId, articleType).stream().map(Article::of).toList();
    }

    public void updateArticle(ArticleDto articleDto, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        article.updateArticle(articleDto);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public void createHelpArticle(HelpFormDto dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        dto.toEntity(user);
    }
}

