package com.woori.bookspring.entity;

import com.woori.bookspring.entity.base.BaseEntity;
import com.woori.bookspring.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;


    // ================ 추가 부분 ===============
    // 유저 한 명당 1개의 장바구니.
    // 처음 장바구니에 상품을 담을때 따로 user를 안넣어줘도 장바구니가 생성될때 자동으로 해당 유저의 장바구니를 갖도록 설정

}
