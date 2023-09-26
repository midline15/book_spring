package com.woori.bookspring.service;

import com.woori.bookspring.dto.BookFormDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.repository.BookCommentRepository;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.CoverRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public BookFormDto getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        return book.of();
    }

    public void updateBook(Long id, BookFormDto bookFormDto, MultipartFile imgFile) throws Exception{ //e북 수정
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 book을 찾을 수 없슴다."));

        Cover cover = coverService.updateCover(book.getCover().getId(),imgFile);
        book.updateBook(bookFormDto, cover);
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);

    }

    @Transactional(readOnly = true)
    public Page<BookFormDto> getBookList(Pageable pageable, String type, String keyword) {
        Page<Book> bookList;

        if ("title".equals(type)) {
            bookList = bookRepository.findByTitleContaining(keyword, pageable);
        } else if ("publisher".equals(type)) {
            bookList = bookRepository.findByPublisherContaining(keyword, pageable);
        } else if ("writer".equals(type)) {
            bookList = bookRepository.findByWriterContaining(keyword, pageable);
        } else if ("rank".equals(type)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("totalSales").descending());
            bookList = bookRepository.findAll(pageable);
        } else {
            bookList = bookRepository.findAll(pageable);
        }

        return bookList.map(Book::of);
    }

    public void calculateAvgScore(Long bookId) {
        bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new).calculateAvgScore();
    }
}
