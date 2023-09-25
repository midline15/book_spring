package com.woori.bookspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {

    private String role;
    private Page<UserManageDto> userManageDtoList;
}

