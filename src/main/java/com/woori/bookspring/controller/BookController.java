package com.woori.bookspring.controller;

import com.woori.bookspring.dto.*;
import com.woori.bookspring.service.BookCommentService;
import com.woori.bookspring.service.BookService;
import com.woori.bookspring.service.PaginationService;
import com.woori.bookspring.service.Paginator;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;
    private final BookCommentService bookCommentService;

    @GetMapping("book") // 리스트조회
    public String bookList(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(required = false) String searchType,
                           @RequestParam(required = false) String searchValue,
                           @PageableDefault(sort = "regTime",direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BookFormDto> bookList = bookService.getBookList(pageable.withPage(page-1),searchType,searchValue);

        int totalPage = bookList.getTotalPages();

        PaginationService paging = new PaginationService();

        model.addAttribute("list", bookList);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, totalPage));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);

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
            return "/book/bookForm";
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
