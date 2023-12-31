package com.woori.bookspring.dto;

import com.woori.bookspring.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;

    public User toEntity() {
        return User.builder()
                .email(email)
                .build();
    }
}
