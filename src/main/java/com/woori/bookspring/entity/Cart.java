package com.woori.bookspring.entity;

import com.woori.bookspring.dto.CartDto;
import com.woori.bookspring.entity.base.BaseEntity;
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
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartBook> cartBookList;

    public static Cart createCart(User user) {
        return Cart.builder()
                .user(user)
                .build();
    }


    public CartDto of() {
        return CartDto.builder()
                .cartBookDtoList(cartBookList.stream().map(CartBook::of).toList())
                .build();
    }
}
