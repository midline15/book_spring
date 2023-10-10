package com.woori.bookspring.controller;

import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.dto.UserUpdateDto;
import com.woori.bookspring.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @Value("${logout_redirect_uri}")
    private String logoutUri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @GetMapping("signup")
    public String signup(@ModelAttribute("dto") SignupForm dto) {
        return "user/signup";
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupForm dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        userService.createUser(dto);
        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
    }

    @PostMapping("nicknameCheck")
    @ResponseBody
    public boolean nicknameCheck(@RequestParam String nickname) {
        return userService.nicknameCheck(nickname);
    }

    @PostMapping("emailCheck")
    @ResponseBody
    public boolean emailCheck(@RequestParam String email) {
        return userService.emailCheck(email);
    }

    @GetMapping("user/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("user/logout/kakao")
    public String kakao(HttpSession session) {
        session.invalidate();

        return "redirect:https://kauth.kakao.com/oauth/logout?client_id="+clientId+"&logout_redirect_uri="+logoutUri;
    }

    @GetMapping("user")
    public String getUser(Model model, Principal principal){

        UserUpdateDto userUpdateDto = userService.getUser(principal.getName());
        model.addAttribute("user",userUpdateDto);
        return "user/userInfo";
    }

    @PatchMapping("user/{user-id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto, Principal principal){
        userService.updateUser(userUpdateDto, principal.getName());
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("user/{user-id}")
    public ResponseEntity<?> disableUser(@PathVariable("user-id") Long userId, Principal principal){
        userService.disableUser(userId, principal.getName());
        return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
    }

}
