package com.woori.bookspring.service;

import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.dto.UserDto;
import com.woori.bookspring.dto.UserUpdateDto;
import com.woori.bookspring.entity.User;
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
    public UserUpdateDto getUser(Long userId) {
         return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new).of();
    }


    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User findUser = userRepository.findById(userUpdateDto.getId()).orElseThrow(EntityNotFoundException::new);
        findUser.updateUser(userUpdateDto);
    }

    @Transactional(readOnly = true)
    public List<User> getUserList() {
        return userRepository.findAll();
    }

}
