package com.woori.bookspring.service;

import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.dto.UserDto;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(SignupForm dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = dto.toEntity();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(String username) {
         return userRepository.findById(username).orElseThrow(EntityNotFoundException::new).of();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(UserDto userDto) {
        userRepository.save(userDto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<User> getUserList() {
        return userRepository.findAll();
    }

}
