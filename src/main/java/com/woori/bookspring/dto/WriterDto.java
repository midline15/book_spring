package com.woori.bookspring.dto;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriterDto {

    private String email;
    private String nickname;
    private String password;
    private String phone;
    private String address;

    public User toEntity() {
        return User.builder()
                .role(Role.WRITER)
                .email(email)
                .nickname(nickname)
                .password(password)
                .phone(phone)
                .address(address)
                .build();
    }
}
