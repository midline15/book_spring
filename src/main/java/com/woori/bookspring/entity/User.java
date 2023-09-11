package com.woori.bookspring.entity;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.dto.UserDto;
import com.woori.bookspring.entity.base.BaseUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User extends BaseUser {

    @Id
    private String username; // email

    private int ticket;

    @Enumerated(EnumType.STRING)
    private OAuthType oauth;

    public UserDto of() {
        return UserDto.builder()
                .username(username)
                .build();
    }
}