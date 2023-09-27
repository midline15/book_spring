package com.woori.bookspring.service;

import com.woori.bookspring.config.auth.UserDetailsImpl;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.*;
import com.woori.bookspring.entity.Ticket;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.controller.exception.UserMissMatchException;
import com.woori.bookspring.repository.TicketRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(SignupForm dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userRepository.save(User.createUser(dto));
        Ticket ticket = Ticket.builder().amount(100).history("가입 축하 선물").user(user).build();
        ticketRepository.save(ticket);
    }

    public void createUser(InsertForm dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userRepository.save(User.createUser(dto));
        Ticket ticket = Ticket.builder().amount(100).history("가입 축하 선물").user(user).build();
        ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public UserUpdateDto getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
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
        ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).updateUser(findUser);
    }

    @Transactional(readOnly = true)
    public Page<UserManageDto> getUserList(Pageable pageable, Role role) {
        return userRepository.findByRole(pageable, role).map(User::forManage);
    }

    public String changeUserStatus(Long userId, UserStatus userStatus) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.changeUserStatus(userStatus);
        return user.getUserStatus().toString();
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

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
