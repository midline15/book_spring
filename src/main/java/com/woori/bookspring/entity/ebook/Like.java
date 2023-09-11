package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.dto.LikeDto;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "likes")
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    public static Like createLike(User user) {
        return Like.builder()
                .user(user)
                .build();
    }

    public LikeDto of() {
        return LikeDto.builder()
                .id(id)
                .build();
    }
}
