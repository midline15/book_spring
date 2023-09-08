package com.woori.bookspring.service;

import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.EbookRepository;
import com.woori.bookspring.repository.EpisodeRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final EbookRepository ebookRepository;

    public void createEpisode(EpisodeFormDto episodeFormDto, Long ebookId) { //에피소드 생성
        Ebook ebook = ebookRepository.findById(ebookId).get();
        episodeRepository.save(Episode.createEpisode(episodeFormDto, ebook));
    }

    @Transactional(readOnly = true)
    public Episode getEpisode(Long id) { //에피소드 조회, 검색
        return episodeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteEpisode(Long id) { //에피소드 삭제
        episodeRepository.deleteById(id);
    }

    public void updateEpisode(Episode episode) { //e북 수정
         episodeRepository.save(episode);
    }

    @Transactional(readOnly = true)
    public List<Episode> getEpisodeList() { //e북 리스트,목록
        return episodeRepository.findAll();
    }
}
