package com.woori.bookspring.service;

import com.woori.bookspring.entity.Book;
import com.woori.bookspring.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    public Book getBook(Long bookId){
        return bookRepository.findById(bookId).get();
    }

    public void updateBook(Book book){
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId){
        bookRepository.findById(bookId);
    }

    public List<Book> bookList(){
        return bookRepository.findAll();
    }
}
