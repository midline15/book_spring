package com.woori.bookspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BillingDto {
    public int amount;
    public String history;

    public static BillingDto signupGift() {
        return BillingDto.builder()
                .amount(100)
                .history("가입 축하 선물")
                .build();
    }
}
