package com.woori.bookspring.controller;

import com.woori.bookspring.entity.Book;
import com.woori.bookspring.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@RequestMapping("book")
@Controller
public class BookController {

    private final BookService bookService;

    @GetMapping("{id}")
    public @ResponseBody String getBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return book.getTitle();
    }
}
