package com.woori.bookspring.service;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.*;
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


    public void disableUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.changeUserStatus(UserStatus.DISABLE);
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User findUser = userRepository.findById(userUpdateDto.getId()).orElseThrow(EntityNotFoundException::new);
        findUser.updateUser(userUpdateDto);
    }

    @Transactional(readOnly = true)
    public List<User> getUserList(Role role) {
        return userRepository.findByRole(role);
    }

    public void changeUserStatus(Long userId, UserStatus userStatus) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.changeUserStatus(userStatus);
    }

    public void createWriter(WriterDto writerDto) {
        writerDto.setPassword(passwordEncoder.encode(writerDto.getPassword()));
        userRepository.save(writerDto.toEntity());
    }

    public void createAdmin(AdminDto adminDto) {
        adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        userRepository.save(adminDto.toEntity());
    }

    public boolean idCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean nicknameCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean emailCheck(String email) {
        return userRepository.existsByEmail(email);
    }
}
