package com.woori.bookspring.entity.base;

import com.woori.bookspring.constant.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseUser extends BaseEntity{

    protected String realName;

    protected String phone;

    protected String password;

    protected String address;

    protected String birth;

    @Enumerated(EnumType.STRING)
    protected Role role;
}
