package com.woori.bookspring.dto;

import com.woori.bookspring.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private Long id;
    private String name;
    private String birth;
    private String address;
    private String phone;

}
