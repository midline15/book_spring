package com.woori.bookspring.service;

import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public void createUser(SignupForm dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = dto.toEntity();
        userRepository.save(user);
    }

    //회원정보
    public User getUser(String username) {
        return userRepository.findById(username).get();
    }

    //회원삭제
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    //회원정보 수정
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);

    }

    //회원정보리스트
    public List<User> getUserList() {
        return userRepository.findAll();
    }

}
