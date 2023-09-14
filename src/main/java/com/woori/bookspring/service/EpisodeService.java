package com.woori.bookspring.service;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.dto.EpisodeUserDto;
import com.woori.bookspring.entity.EpisodeUser;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.repository.EbookRepository;
import com.woori.bookspring.repository.EpisodeRepository;
import com.woori.bookspring.repository.EpisodeUserRepository;
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
    private final EpisodeUserRepository episodeUserRepository;
    private final UserRepository userRepository;

    public void createEpisode(EpisodeFormDto episodeFormDto, Long ebookId) { //에피소드 생성
        Ebook ebook = ebookRepository.findById(ebookId).get();
        episodeRepository.save(Episode.createEpisode(episodeFormDto, ebook));
    }


    @Transactional(readOnly = true)
    public EpisodeFormDto getEpisode(Long id) { //에피소드 조회, 검색
        return episodeRepository.findById(id).orElseThrow(EntityNotFoundException::new).of();
    }

    public void deleteEpisode(Long id) { //에피소드 삭제
        episodeRepository.deleteById(id);
    }

    public void updateEpisode(EpisodeFormDto episodeFormDto) {
        Episode episode = episodeRepository.findById(episodeFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        episode.updateEpisode(episodeFormDto);
    }

    @Transactional(readOnly = true)
    public List<Episode> getEpisodeList() { //e북 리스트,목록
        return episodeRepository.findAll();
    }

    public void createEpisode(EpisodeFormDto episodeFormDto) {
        Ebook ebook = ebookRepository.findById(episodeFormDto.getEbookId()).orElseThrow(EntityNotFoundException::new);
        episodeRepository.save(Episode.createEpisode(episodeFormDto, ebook));

    }

    public List<EpisodeUserDto> getEpisodeUserList(String email) {
        return episodeUserRepository.findByUser_Email(email).stream().map(EpisodeUser::of).toList();
    }

    public void buyEpisode(Long episodeId, String email) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        user.useTicket(episode.getEbook().getPrice());
        episodeUserRepository.save(EpisodeUser.createEpisodeUser(episode, user));
    }
}
