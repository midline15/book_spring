package com.woori.bookspring.service;

import com.woori.bookspring.dto.BookDto;
import com.woori.bookspring.dto.BookFormDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.CoverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CoverService coverService;
    private final CoverRepository coverRepository;

    public void createBook(BookFormDto bookFormDto, MultipartFile imgFile) throws Exception {
        Cover cover = coverService.saveBookCover(imgFile);
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
        bookRepository.findById(bookId);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBookList() {
        return bookRepository.findAll().stream().map(Book::of).toList();
    }
}
