package com.woori.bookspring.service;

import com.woori.bookspring.dto.CommentDto;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.board.Comment;
import com.woori.bookspring.repository.ArticleRepository;
import com.woori.bookspring.repository.CommentRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public void createComment(Long articleId, CommentDto commentDto, String email) {
        Article article = articleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        commentRepository.save(commentDto.toEntity(article, user));
    }

    public void updateComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(EntityNotFoundException::new);
        comment.updateComment(commentDto);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentList(Long userId) {
        return commentRepository.findByUserId(userId).stream().map(Comment::of).toList();
    }
}
