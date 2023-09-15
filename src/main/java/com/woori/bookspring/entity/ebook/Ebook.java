package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.constant.SellStatus;
import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.base.BaseBook;
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

    private String title;
    private String intro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private Cover cover;

    @OneToMany(mappedBy = "ebook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodeList;

    @OneToMany(mappedBy = "ebook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEbook> likeEbookList;

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;

    public EbookFormDto of() {
        return EbookFormDto.builder()
                .id(id)
                .title(title)
                .intro(intro)
                .price(price)
                .url(cover.getUrl())
                .coverId(cover.getId())
                .episodeList(episodeList.stream().map(Episode::of).toList())
                .build();

    }

    public Ebook updateEbook(EbookFormDto dto, Cover cover) {
        this.title = dto.getTitle();
        this.intro = dto.getIntro();
        this.price = dto.getPrice();
        this.cover = cover;
        return this;
    }
}
