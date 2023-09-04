package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.base.BaseBook;
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
public class EBook extends BaseBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private User writer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private Cover cover;
}
