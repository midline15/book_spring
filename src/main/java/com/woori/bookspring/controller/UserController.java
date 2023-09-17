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

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @Value("${logout_redirect_uri}")
    private String logoutUri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @GetMapping("user/signup")
    public String signup(@ModelAttribute("dto") SignupForm dto) {
        return "user/signup";
    }

    @PostMapping("user/signup")
    public String signup(@Valid @ModelAttribute("dto") SignupForm dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "user/signup";
        }
        userService.createUser(dto);
        return "redirect:/";
    }

    @PostMapping("user/nicknameCheck")
    @ResponseBody
    public boolean nicknameCheck(@RequestParam String nickname) {
        return userService.nicknameCheck(nickname);
    }

    @PostMapping("user/emailCheck")
    @ResponseBody
    public boolean emailCheck(@RequestParam String email) {
        return userService.emailCheck(email);
    }

    @PostMapping("user/id")
    public String idCheck(SignupForm dto, Model model){
        boolean flag = userService.idCheck(dto.getEmail());
        if (flag) {
            dto.setIdCheck("N");
        }else {
            dto.setIdCheck("Y");
        }
        model.addAttribute("dto", dto);
        return "user/signup";
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

    @GetMapping("user/{user-id}")
    public String getUser(@PathVariable("user-id") Long userId, Model model){
        UserUpdateDto userUpdateDto = userService.getUser(userId);
        model.addAttribute("user",userUpdateDto);
        return "user/user";
    }

    @PatchMapping("user/{user-id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto){
        userService.updateUser(userUpdateDto);
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("user/{user-id}")
    public ResponseEntity<?> disableUser(@PathVariable("user-id") Long userId){
        userService.disableUser(userId);
        return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
    }

}
