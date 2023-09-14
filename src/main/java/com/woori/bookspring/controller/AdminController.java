package com.woori.bookspring.controller;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.AdminDto;
import com.woori.bookspring.dto.WriterDto;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("admin/user")
    public String getUserList(Model model){
        List<User> userList = userService.getUserList(Role.USER);
        model.addAttribute("list", userList);
        return "admin/userList";
    }

    @PatchMapping("admin/user/{user-id}")
    public ResponseEntity<?> changeUserStatus(@PathVariable("user-id") Long userId, @RequestParam String userStatus){
        userService.changeUserStatus(userId, UserStatus.valueOf(userStatus));
        return new ResponseEntity<>(userStatus+"완료", HttpStatus.OK);
    }

    @GetMapping("admin/writer")
    public String getWriterList(Model model){
        List<User> writerList = userService.getUserList(Role.WRITER);
        model.addAttribute("list", writerList);
        return "admin/writerList";
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

    @GetMapping("admin")
    public String getAdminList(Model model){
        List<User> adminList = userService.getUserList(Role.ADMIN);
        model.addAttribute("list", adminList);
        return "admin/adminList";
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

}
