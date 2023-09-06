package com.woori.bookspring.entity.user;

import com.woori.bookspring.constant.OAuthType;
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

    @Enumerated(EnumType.STRING)
    private OAuthType oauth;
}