package com.woori.bookspring.controller;

import com.woori.bookspring.entity.Book;
import com.woori.bookspring.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("book")
@Controller
public class BookController {

    private final BookService bookService;

    @GetMapping // 리스트조회
    public @ResponseBody List<Book> bookList(){
        return bookService.bookList();
    }

    @GetMapping("{id}") // 단건조회
    public @ResponseBody String getBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return book.getTitle();
    }

    @GetMapping("new")
    public String newBook(){
        return "book/bookForm";
    }
    @PostMapping("new") // newBook
    public @ResponseBody String createBook(@RequestBody Book newBook) {
        return bookService.createBook(newBook).getId().toString();
    }
    @DeleteMapping("{id}") // 삭제
    public @ResponseBody String deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return "삭제 완료";
    }
    @PatchMapping("{id}") // 수정
    public @ResponseBody String updateBook(@PathVariable Long id, @RequestBody Book updateBook) {
        bookService.updateBook(updateBook);
        return "수정 완료";
    }
}
