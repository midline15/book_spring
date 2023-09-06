package com.woori.bookspring.entity.user;

import com.woori.bookspring.constant.Role;
import com.woori.bookspring.entity.base.BaseUser;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Writer extends BaseUser {

    @Id
    private String username; // email
}