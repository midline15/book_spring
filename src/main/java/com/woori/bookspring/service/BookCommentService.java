package com.woori.bookspring.service;

import com.woori.bookspring.entity.BookComment;
import com.woori.bookspring.repository.BookCommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    public void createBookComment(BookComment comment){
        bookCommentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public BookComment getBookComment(Long bookCommentId){
        return bookCommentRepository.findById(bookCommentId).orElseThrow(EntityNotFoundException::new);
    }

    public void updateBookComment(BookComment bookComment){
        bookCommentRepository.save(bookComment);
    }

    public void deleteBookComment(Long bookCommentId){
        bookCommentRepository.deleteById(bookCommentId);
    }

    @Transactional(readOnly = true)
    public List<BookComment> getBookCommentList(){
        return bookCommentRepository.findAll();
    }
}
