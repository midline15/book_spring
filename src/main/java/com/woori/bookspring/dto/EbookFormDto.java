package com.woori.bookspring.dto;

import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.ebook.Ebook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbookFormDto {

    private Long id;
    private String title;
    private String intro;
    private String writerNickname;
    private int price;
    private int totalSales;
    private float avgScore;
    private Long coverId;
    private String url;
    private Cover cover;
    private List<EpisodeFormDto> episodeList;
    private Long inventoryEbookId;
    private Long likeEbookId;


    public Ebook toEntity(User user, Cover cover){
        return Ebook.builder()
                .user(user)
                .title(title)
                .intro(intro)
                .cover(cover)
                .price(price)
                .build();
    }
}
