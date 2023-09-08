package com.woori.bookspring.controller;

import com.woori.bookspring.dto.BookDto;
import com.woori.bookspring.dto.BookFormDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("book")
@Controller
public class BookController {

    private final BookService bookService;

    @GetMapping // 리스트조회
    public String bookList(Model model){
        List<BookDto> bookList = bookService.getBookList();
        model.addAttribute("list",bookList);
        return "book/bookList";
    }

    @GetMapping("{id}") // 단건조회
    public String getBook(@PathVariable Long id, Model model) {
        BookDto bookDto = bookService.getBook(id);
        model.addAttribute("book", bookDto);

        return "book/book";
    }

    @GetMapping("new")
    public String newBook(){
        return "book/bookForm";
    }
    @PostMapping("new") // newBook
    public String createBook(Model model, BookFormDto bookFormDto, @RequestParam("imgFile") MultipartFile imgFile) {
        try {
            bookService.createBook(bookFormDto, imgFile);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "book/bookForm";
        }
        return "redirect:/";
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
