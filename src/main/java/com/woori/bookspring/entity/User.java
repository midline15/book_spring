package com.woori.bookspring.entity;

import com.woori.bookspring.constant.OAuthType;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.constant.UserStatus;
import com.woori.bookspring.dto.InsertForm;
import com.woori.bookspring.dto.SignupForm;
import com.woori.bookspring.dto.UserManageDto;
import com.woori.bookspring.dto.UserUpdateDto;
import com.woori.bookspring.entity.base.BaseEntity;
import com.woori.bookspring.entity.ebook.Inventory;
import com.woori.bookspring.entity.ebook.Like;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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

    private int totalTicket;

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

   /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL ,orphanRemoval = true)
    @Builder.Default
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Ticket> ticketList = new ArrayList<>();*/

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inventory inventory;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Like like;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EpisodeUser> episodeUserList;

    public static User createUser(SignupForm dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .address(dto.getAddress())
                .birth(dto.getBirth())
                .phone(dto.getPhone())
                .role(Role.USER)
                .totalTicket(100)
                .build();
    }
    public static User createUser(InsertForm dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .address(dto.getAddress())
                .birth(dto.getBirth())
                .phone(dto.getPhone())
                .role(Role.valueOf(dto.getRole()))
                .build();
    }
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
  
    public void changeUserStatus(UserStatus userStatus) {
        if(userStatus == UserStatus.ACTIVE){
            this.userStatus = UserStatus.BLOCK;
        }else if (userStatus == UserStatus.BLOCK){
            this.userStatus = UserStatus.ACTIVE;
        }else if(userStatus == UserStatus.DISABLE && this.userStatus == UserStatus.DISABLE){
            this.userStatus = UserStatus.ACTIVE;
        }else if(userStatus == UserStatus.DISABLE && this.userStatus == UserStatus.ACTIVE){
            this.userStatus = userStatus;
        }
    }

    public void addTicket(int amount) {
        totalTicket += amount;
    }

    public boolean useTicket(int amount) {
        if (totalTicket < amount){
            return false;
        }
        totalTicket -= amount;
        return true;
    }

    public UserManageDto forManage() {
        return UserManageDto.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .userStatus(userStatus.toString())
                .build();
    }
}