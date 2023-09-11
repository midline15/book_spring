package com.woori.bookspring.dto;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupForm {
    private String username;
    private String password;
    private String realName;
    private String address;
    private String birth;
    private String phone;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .realName(realName)
                .address(address)
                .birth(birth)
                .phone(phone)
                .oauth(OAuthType.WOORI)
                .build();
    }
}
