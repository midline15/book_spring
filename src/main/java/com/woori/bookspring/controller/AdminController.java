package com.woori.bookspring.controller;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.*;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.service.ArticleService;
import com.woori.bookspring.service.PaginationService;
import com.woori.bookspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        int totalPage = userManageDtoList.getTotalPages();
        UserListDto userListDto = new UserListDto(role, userManageDtoList, totalPage);

        PaginationService paging = new PaginationService();

        model.addAttribute("list", userListDto);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, totalPage));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);

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

    @PostMapping("admin/writer")
    public String createWriter(WriterDto writerDto){
        userService.createWriter(writerDto);
        return "redirect:/admin/writer";
    }

    @DeleteMapping("admin/writer/{writer-id}")
    public ResponseEntity<?> disableWriter(@PathVariable("writer-id") Long writerId){
        userService.changeUserStatus(writerId, UserStatus.DISABLE);
        return new ResponseEntity<>("완료", HttpStatus.OK);
    }

    @PostMapping("admin")
    public String createAdmin(AdminDto adminDto){
        userService.createAdmin(adminDto);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{admin-id}")
    public ResponseEntity<?> disableAdmin(@PathVariable("admin-id") Long adminId){
        userService.changeUserStatus(adminId, UserStatus.DISABLE);
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

        int totalPage = articleList.getTotalPages();

        PaginationService paging = new PaginationService();

        model.addAttribute("list", articleList);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, totalPage));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        return "admin/questionList";
    }

    @GetMapping("admin/insert")
    public String insertUser(){
        return "admin/insert";
    }
}
