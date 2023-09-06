package com.woori.bookspring.controller;

import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.repository.UserRepository;
import com.woori.bookspring.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/logout/kakao")
    public String kakao(HttpSession session) {
        session.invalidate();

        return "redirect:https://kauth.kakao.com/oauth/logout?client_id="+clientId+"&logout_redirect_uri="+logoutUri;
    }
}
