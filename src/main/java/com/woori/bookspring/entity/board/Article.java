package com.woori.bookspring.entity.board;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.ArticleDto;
import com.woori.bookspring.dto.ArticleFormDto;
import com.woori.bookspring.dto.HelpFormDto;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true) //n+1
    private List<Comment> commentList;
  
    public ArticleDto of() {
        return ArticleDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .commentDtoList(commentList.stream().map(Comment::of).toList())
                .regTime(getRegTime())
                .createdBy(getCreatedBy())
                .articleType(articleType.toString())
                .commentDtoList(commentList.stream().map(Comment::of).toList())
                .build();
    }

    public void updateArticle(ArticleDto dto) {
        title = dto.getTitle();
        content = dto.getContent();
    }
}
