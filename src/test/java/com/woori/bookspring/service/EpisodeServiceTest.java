package com.woori.bookspring.service;

import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.entity.ebook.Episode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EpisodeServiceTest {

    @Autowired
    EpisodeService episodeService;

    @Test
    @DisplayName("에피소드 추가")
    public void createEpisodeTest() {
        for (int i = 0; i<10; i++){
            EpisodeFormDto 테스트_입니다 = EpisodeFormDto.builder()
                    .title("test" + i)
                    .content("테스트 입니다")
                    .build();

            episodeService.createEpisode(테스트_입니다,Long.valueOf(1));
        }
        episodeService.getEpisodeList().forEach(episode -> System.out.println(episode.getTitle()));
    }
}