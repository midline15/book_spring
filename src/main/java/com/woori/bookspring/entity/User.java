package com.woori.bookspring.entity;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.UserUpdateDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private int ticket;

    private String nickname;

    private String phone;

    private String password;

    private String address;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OAuthType oauth = OAuthType.WOORI;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL ,orphanRemoval = true)
    @Builder.Default
    private List<Order> orderList = new ArrayList<>();

    /*@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inventory inventory;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Like like;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Cart cart;*/

    public UserUpdateDto of() {
        return UserUpdateDto.builder()
                .id(id)
                .nickname(nickname)
                .address(address)
                .birth(birth)
                .phone(phone)
                .build();
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        nickname = userUpdateDto.getNickname();
        birth = userUpdateDto.getBirth();
        phone = userUpdateDto.getPhone();
        address = userUpdateDto.getAddress();
    }

  
    public void useTicket(int price) {
        ticket -= price;
    }
  
    public void changeUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}