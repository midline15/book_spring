package com.woori.bookspring.entity;

import com.woori.bookspring.dto.CoverDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Cover extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    private String origName;

    public Cover updateCover(String origName, String name, String url) {
        this.origName = origName;
        this.name = name;
        this.url = url;
        return this;
    }

    public CoverDto of(){
        return CoverDto.builder()
                .id(id)
                .name(name)
                .url(url)
                .origName(origName)
                .build();
    }
}