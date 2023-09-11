package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.entity.base.BaseBook;
import com.woori.bookspring.entity.User;
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
public class Ebook extends BaseBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private Cover cover;

    @OneToMany(mappedBy = "ebook",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodeList;
}
