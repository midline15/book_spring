package com.woori.bookspring.controller;

import com.woori.bookspring.dto.*;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.service.BookCommentService;
import com.woori.bookspring.service.BookService;
import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;
    private final BookCommentService bookCommentService;

    @GetMapping("book") // 리스트조회
    public String bookList(Model model, @RequestParam(value = "searchType", required = false) String searchType,
                           @RequestParam(value = "searchValue", required = false) String searchValue){

        SearchParam searchParam = new SearchParam();
        searchParam.setSearchType(searchType);
        searchParam.setSearchValue(searchValue);

        // 도서목록조회
        List<BookFormDto> bookList = bookService.getBookList(searchType,searchValue);


        model.addAttribute("list",bookList);

        model.addAttribute("param", searchParam);
        return "book/bookList";
    }

    @GetMapping("book/{book-id}") // 단건조회
    public String getBook(@PathVariable("book-id") Long id, Model model) {
        BookFormDto bookDto = bookService.getBook(id);
        model.addAttribute("book", bookDto);

        return "book/book";
    }

    @GetMapping("admin/book/new")
    public String createBook(@ModelAttribute("book") BookFormDto bookFormDto){
        return "book/bookForm";
    }


    @PostMapping("admin/book") // newBook
    public String createBook(Model model, @Valid @ModelAttribute("book") BookFormDto bookFormDto, BindingResult bindingResult, @RequestParam("imgFile") MultipartFile imgFile) {

        if (bindingResult.hasErrors()){
            return "book/bookForm";
        }

        try {
            bookService.createBook(bookFormDto, imgFile);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "bookForm";
        }
        return "redirect:/book";
    }
    @DeleteMapping("admin/book/{book-id}") // 삭제
    public @ResponseBody ResponseEntity<?> deleteBook
            (@PathVariable("book-id") Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PostMapping("book/{book-id}/book-comment")
    public String createBookComment(BookCommentDto bookCommentDto, Principal principal){
        bookCommentService.createBookComment(bookCommentDto, principal.getName());
        return "redirect:/book/{book-id}";
    }

    @PatchMapping("book/{book-id}/book-comment/{book-comment-id}")
    public ResponseEntity<?> updateBookComment(@RequestBody BookCommentDto bookCommentDto){
        bookCommentService.updateBookComment(bookCommentDto);
        return new ResponseEntity<>("수정완료", HttpStatus.OK);
    }

    @DeleteMapping("book/{book-id}/book-comment/{book-comment-id}")
    public ResponseEntity<?> deleteBookComment(@PathVariable("book-comment-id") Long bookCommentId, @PathVariable("book-id") Long bookId){
        bookCommentService.deleteBookComment(bookCommentId);
        bookService.calculateAvgScore(bookId);
        return new ResponseEntity<>("댓글 삭제", HttpStatus.OK);
    }

    @GetMapping("/admin/book/{book-id}")
    public String updateBook(@PathVariable("book-id") Long bookId, Model model){
        model.addAttribute("book",bookService.getBook(bookId));
        return "book/bookForm";
    }

    @PatchMapping("/admin/book/{book-id}")
    public ResponseEntity<?> updateBook(@PathVariable("book-id") Long bookId, BookFormDto bookFormDto, @RequestParam("imgFile") MultipartFile imgFile, Model model) {

        try {
            bookService.updateBook(bookId, bookFormDto, imgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생 하였습니다.");
            return new ResponseEntity<>("실패", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("성공", HttpStatus.OK);
    }
}
