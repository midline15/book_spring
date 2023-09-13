package com.woori.bookspring.controller;

import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.dto.UserUpdateDto;
import com.woori.bookspring.repository.UserRepository;
import com.woori.bookspring.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @Value("${logout_redirect_uri}")
    private String logoutUri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @GetMapping("signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("signup")
    public String signup(SignupForm dto){
        userService.createUser(dto);
        return "redirect:/";
    }

    @GetMapping("login")
    public String login(){
        return "user/login";
    }

    @GetMapping("logout/kakao")
    public String kakao(HttpSession session) {
        session.invalidate();

        return "redirect:https://kauth.kakao.com/oauth/logout?client_id="+clientId+"&logout_redirect_uri="+logoutUri;
    }

    @GetMapping("{user-id}")
    public String getUser(@PathVariable("user-id") Long userId, Model model){
        UserUpdateDto userUpdateDto = userService.getUser(userId);
        model.addAttribute("user",userUpdateDto);
        return "user/user";
    }

    @PatchMapping("{user-id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto){
        userService.updateUser(userUpdateDto);
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("{user-id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
    }

}
