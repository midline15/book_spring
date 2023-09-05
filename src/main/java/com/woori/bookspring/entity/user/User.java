package com.woori.bookspring.entity.user;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.entity.base.BaseEntity;
import com.woori.bookspring.entity.base.BaseUser;
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
public class User extends BaseUser {

    @Id
    private String username; // email
}