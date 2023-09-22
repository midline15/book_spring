package com.woori.bookspring.service;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.AdminDto;
import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.dto.UserUpdateDto;
import com.woori.bookspring.dto.WriterDto;
import com.woori.bookspring.entity.Billing;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.exception.UserMissMatchException;
import com.woori.bookspring.repository.BillingRepository;
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
    private final BillingRepository billingRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(SignupForm dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userRepository.save(User.createUser(dto));
        Billing billing  = Billing.builder().amount(100).history("가입 축하 선물").user(user).build();
        billingRepository.save(billing);
    }

    @Transactional(readOnly = true)
    public UserUpdateDto getUser(Long userId, String email) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        validateUser(user.getEmail(), email);
        return user.of();
    }


    public void disableUser(Long userId, String email) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        validateUser(user.getEmail(), email);
        user.changeUserStatus(UserStatus.DISABLE);
    }

    public void updateUser(UserUpdateDto userUpdateDto, String email) {
        User findUser = userRepository.findById(userUpdateDto.getId()).orElseThrow(EntityNotFoundException::new);
        validateUser(findUser.getEmail(), email);
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

    public boolean nicknameCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean emailCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validateUser(String findEmail, String email){
        if (!findEmail.equals(email)) throw new UserMissMatchException();
    }
}
