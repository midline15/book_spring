package com.woori.bookspring.service;

import com.woori.bookspring.entity.board.Comment;
import com.woori.bookspring.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    //댓글 작성
    @Transactional
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    //댓글 조회
    public void getComment(Long id) {
        commentRepository.findById(id).get();
    }

    //댓글 전체 조회
    public List<Comment> getCommentList(){
        return commentRepository.findAll();
    }

    //댓글 수정
    @Transactional
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
