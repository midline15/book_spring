package com.woori.bookspring.entity;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.dto.UserDto;
import com.woori.bookspring.dto.UserUpdateDto;
import com.woori.bookspring.entity.base.BaseEntity;
import com.woori.bookspring.entity.ebook.Inventory;
import com.woori.bookspring.entity.ebook.Like;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    private String name;

    private String phone;

    private String password;

    private String address;

    private String birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private OAuthType oauth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Inventory inventory;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Like like;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    public UserUpdateDto of() {
        return UserUpdateDto.builder()
                .id(id)
                .name(name)
                .address(address)
                .birth(birth)
                .phone(phone)
                .build();
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        name = userUpdateDto.getName();
        birth = userUpdateDto.getBirth();
        phone = userUpdateDto.getPhone();
        address = userUpdateDto.getAddress();
    }
}