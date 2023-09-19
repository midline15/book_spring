package com.woori.bookspring.dto;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupForm {

    @Email(message = "이메일을 입력해 주세요.")
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String passwordCheck;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String nickname;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;

    @NotNull(message = "생일을 입력해 주세요.")
    @Past
    private LocalDate birth;

    @NotBlank(message = "전화번호를 입력해 주세요.")
    private String phone;

    @Pattern(regexp = "Y", message = "중복 검사를 해주세요.")
    private String idCheck;
}
