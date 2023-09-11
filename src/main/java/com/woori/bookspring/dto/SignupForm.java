package com.woori.bookspring.dto;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupForm {
    private String email;
    private String password;
    private String name;
    private String address;
    private String birth;
    private String phone;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .birth(birth)
                .phone(phone)
                .oauth(OAuthType.WOORI)
                .build();
    }
}
