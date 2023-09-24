package com.woori.bookspring.service;

import com.woori.bookspring.config.auth.UserDetailsImpl;
import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.dto.EpisodeUserDto;
import com.woori.bookspring.entity.Ticket;
import com.woori.bookspring.entity.EpisodeUser;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final TicketRepository ticketRepository;

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

    public List<EpisodeUserDto> getEpisodeUserList(String email, Long ebookId) {
        return episodeUserRepository.findByUser_EmailAndEpisode_EbookId(email, ebookId).stream().map(EpisodeUser::of).toList();
    }

    public void buyEpisode(Long episodeId, String email) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        Ebook ebook = episode.getEbook();
        user.useTicket(ebook.getPrice());
        ebook.sellEpisode();

        ticketRepository.save(Ticket.useTicket(episode, user));
        episodeUserRepository.save(EpisodeUser.createEpisodeUser(episode, user));
        ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).updateUser(user);
    }
}
