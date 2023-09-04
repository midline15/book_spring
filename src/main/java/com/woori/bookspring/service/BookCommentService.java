package com.woori.bookspring.service;

import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.BookComment;
import com.woori.bookspring.repository.BookCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BookCommentService {
    private final BookCommentRepository bookCommentRepository;

    public void createBookComment(BookComment comment){
        bookCommentRepository.save(comment);
    }

    public BookComment getBookComment(Long bookCommentId){
        return bookCommentRepository.findById(bookCommentId).get();
    }

    public void updateBookComment(BookComment bookComment){
        bookCommentRepository.save(bookComment);
    }

    public void deleteBookComment(Long bookCommentId){
        bookCommentRepository.deleteById(bookCommentId);
    }

    public List<BookComment> bookCommentList(){
        return bookCommentRepository.findAll();
    }
}
