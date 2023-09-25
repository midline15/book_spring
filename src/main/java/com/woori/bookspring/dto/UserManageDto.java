package com.woori.bookspring.dto;

import com.woori.bookspring.constant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserManageDto {

    private Long id;
    private String email;
    private String nickname;
    private String userStatus;
}
