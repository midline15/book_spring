package com.woori.bookspring.service;

import com.woori.bookspring.dto.BookCommentDto;
import com.woori.bookspring.dto.CommentDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.BookComment;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.board.Comment;
import com.woori.bookspring.repository.BookCommentRepository;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BookCommentService {

    private final BookCommentRepository bookCommentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public void createBookComment(BookCommentDto bookCommentDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Book book = bookRepository.findById(bookCommentDto.getBookId()).orElseThrow(EntityNotFoundException::new);
        bookCommentRepository.save(BookComment.createBookComment(user, book, bookCommentDto));
        book.calculateAvgScore();
    }

    @Transactional(readOnly = true)
    public BookComment getBookComment(Long bookCommentId){
        return bookCommentRepository.findById(bookCommentId).orElseThrow(EntityNotFoundException::new);
    }

    public void updateBookComment(BookCommentDto bookCommentDto){
        BookComment bookComment = bookCommentRepository.findById(bookCommentDto.getId()).orElseThrow(EntityNotFoundException::new);
        bookComment.updateBookComment(bookCommentDto);
    }

    public void deleteBookComment(Long bookCommentId){
        bookCommentRepository.deleteById(bookCommentId);
    }

    @Transactional(readOnly = true)
    public Page<BookCommentDto> getBookCommentList(Pageable pageable, Long userId) {
        return bookCommentRepository.findByUserId(pageable, userId).map(BookComment::of);
    }
}
