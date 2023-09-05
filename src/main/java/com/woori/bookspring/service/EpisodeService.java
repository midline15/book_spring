package com.woori.bookspring.service;

import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.repository.EpisodeRepository;
<<<<<<< Updated upstream
import lombok.RequiredArgsConstructor;
=======
>>>>>>> Stashed changes
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

<<<<<<< Updated upstream
@Transactional
@RequiredArgsConstructor
@Service
=======
>>>>>>> Stashed changes
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    public Episode createEpisode(Episode episode) { //에피소드 생성
        return episodeRepository.save(episode);
    }

    public Episode getEpisode(Long id) { //에피소드 조회, 검색
        return episodeRepository.findById(id).get();
    }

    public void deleteEpisode(Long id) { //에피소드 삭제
        episodeRepository.deleteById(id);
    }

    public void updateEpisode(Episode episode) { //e북 수정
         episodeRepository.save(episode);
    }

    public List<Episode> getEpisodeList() { //e북 리스트,목록
        return episodeRepository.findAll();
    }
}
