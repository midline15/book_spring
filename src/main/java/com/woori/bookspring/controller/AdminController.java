package com.woori.bookspring.controller;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.*;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.service.ArticleService;
import com.woori.bookspring.service.PaginationService;
import com.woori.bookspring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("admin/{role}")
    public String getUserList(
            @PathVariable("role") String role,
            @PageableDefault(size=20, sort = "id") Pageable pageable,
            @RequestParam(defaultValue = "1",required = false) int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchValue,
            Model model){

        Role userRole = Role.valueOf(role.toUpperCase());
        Page<UserManageDto> userManageDtoList = userService.getUserList(pageable, userRole);
        model.addAttribute("role", role);
        PaginationService.pagination(model, userManageDtoList, page, searchType, searchValue);

        return "admin/userList";
    }

    @PatchMapping("admin/user/{user-id}")
    public ResponseEntity<?> changeUserStatus(@RequestParam String userStatus, @PathVariable("user-id") Long userId){
        String changed = userService.changeUserStatus(userId, UserStatus.valueOf(userStatus));
        return new ResponseEntity<>(changed+"완료", HttpStatus.OK);
    }

    @DeleteMapping("admin/user/{user-id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("삭제", HttpStatus.OK);
    }

    @DeleteMapping("admin/{role}/{user-id}")
    public ResponseEntity<?> disableUser(@PathVariable("user-id") Long userId){
        userService.changeUserStatus(userId, UserStatus.DISABLE);
        return new ResponseEntity<>("완료", HttpStatus.OK);
    }

    @GetMapping("admin/question")
    public String getQuestionList(
            @PageableDefault(sort="regTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchValue,
            Model model){

        Page<ArticleDto> articleList = articleService.getArticleList(pageable.withPage(page-1), ArticleType.QUESTION, searchType, searchValue);
        PaginationService.pagination(model, articleList, page, searchType, searchValue);

        return "admin/questionList";
    }

    @GetMapping("admin/insert")
    public String insertUser(@ModelAttribute("dto") InsertForm dto){
        return "user/signup";
    }

    @PostMapping("admin/insert")
    public ResponseEntity<?> signup(@Valid @RequestBody InsertForm dto, BindingResult bindingResult, Authentication authentication){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if(dto.getRole().equals("ADMIN") && !authentication.getAuthorities().toArray()[0].toString().equals("SUPER")) {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        userService.createUser(dto);
        return new ResponseEntity<>("등록 완료", HttpStatus.OK);
    }
}
