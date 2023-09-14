package com.woori.bookspring.service;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.ArticleDto;
import com.woori.bookspring.dto.ArticleFormDto;
<<<<<<< Updated upstream
import com.woori.bookspring.dto.HelpFormDto;
=======
import com.woori.bookspring.dto.CommentDto;
>>>>>>> Stashed changes
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.board.Comment;
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
    private final UserRepository userRepository;

    public void createArticle(ArticleFormDto articleFormDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Article article = articleFormDto.toEntity(user);
        articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        return article.of();
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getArticleList(ArticleType articleType) {
        return articleRepository.findByArticleType(articleType).stream().map(Article::of).toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getArticleList(Long userId, ArticleType articleType){
        return articleRepository.findByUserIdAndArticleType(userId, articleType).stream().map(Article::of).toList();
    }

<<<<<<< Updated upstream

    public void updateArticle(HelpFormDto helpFormDto) {
        Article article = articleRepository.findById(helpFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        article.updateArticle(helpFormDto);
=======
    @Transactional
    public void updateArticle(ArticleDto articleDto, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        articleRepository.save(article);
>>>>>>> Stashed changes
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

<<<<<<< Updated upstream
    public void createHelpArticle(HelpFormDto dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        dto.toEntity(user);
    }

    public void updateArticle(Article article) {

=======
    public void createArticleComment(Long articleId, CommentDto commentDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Article article = articleRepository.findById(articleId).orElseThrow();

        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setUser(user);
        comment.setContent(commentDto.getContent());

        articleRepository.save(comment.getArticle());
>>>>>>> Stashed changes
    }
}
