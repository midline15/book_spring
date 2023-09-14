package com.woori.bookspring.entity.ebook;

import com.woori.bookspring.dto.EbookFormDto;
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
public class LikeEbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likes_id")
    private Like likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ebook_id")
    private Ebook ebook;

    public static LikeEbook createLikeEbook(Ebook ebook, Like like){
        return LikeEbook.builder()
                .ebook(ebook)
                .likes(like)
                .build();
    }

    public EbookFormDto of() {
        return EbookFormDto.builder()
                .id(ebook.getId())
                .likeEbookId(id)
                .title(ebook.getTitle())
                .intro(ebook.getIntro())
                .url(ebook.getCover().getUrl())
                .build();
    }
}
