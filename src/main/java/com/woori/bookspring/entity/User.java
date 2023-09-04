package com.woori.bookspring.entity;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    private String username; // email

    private String realName;

    private String phone;

    private String password;

    private String address;

    private String birth;

    @Enumerated(EnumType.STRING)
    private Role role;
}