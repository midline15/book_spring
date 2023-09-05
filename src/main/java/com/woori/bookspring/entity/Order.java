package com.woori.bookspring.entity;

import com.woori.bookspring.constant.OrderStatus;
import com.woori.bookspring.entity.base.BaseEntity;
import com.woori.bookspring.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}