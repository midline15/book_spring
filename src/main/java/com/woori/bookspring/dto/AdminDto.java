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
public class AdminDto {

    private String email;
    private String password;
    private String name;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(Role.ADMIN)
                .build();
    }
}