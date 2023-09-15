package com.woori.bookspring.service;

import com.woori.bookspring.dto.BookCommentDto;
import com.woori.bookspring.dto.BookDto;
import com.woori.bookspring.dto.BookFormDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.BookComment;
import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.repository.BookCommentRepository;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.CoverRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CoverService coverService;
    private final CoverRepository coverRepository;
    private final BookCommentRepository bookCommentRepository;
    private final UserRepository userRepository;

    //책등록
    public void createBook(BookFormDto bookFormDto, MultipartFile imgFile)
            throws Exception {

        Cover cover = coverService.saveCover(imgFile);
        Book book = bookFormDto.toEntity(cover);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public BookDto getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        return book.of();
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);

    }

    @Transactional(readOnly = true)
    public List<BookDto> getBookList(String type, String keyword) {
        List<Book> bookList;
        if ("title".equals(type)) {
            bookList = bookRepository.findByTitleContaining(keyword);
        } else if ("publisher".equals(type)) {
            bookList = bookRepository.findByPublisherContaining(keyword);
        } else {
            bookList = bookRepository.findAll();
        }

        return bookList.stream().map(Book::of).toList();
    }

    @Transactional // 댓글달기
    public void createBookComment(BookCommentDto bookCommentDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Book book = bookRepository.findById(bookCommentDto.getBookId()).orElseThrow(EntityNotFoundException::new);

        bookCommentRepository.save(BookComment.createBookComment(user, book, bookCommentDto));
    }
}
