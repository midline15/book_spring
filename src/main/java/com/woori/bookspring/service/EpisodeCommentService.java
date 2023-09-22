package com.woori.bookspring.service;

import com.woori.bookspring.dto.EpisodeCommentDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.BookComment;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.entity.ebook.EpisodeComment;
import com.woori.bookspring.repository.EpisodeCommentRepository;
import com.woori.bookspring.repository.EpisodeRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeCommentService {
    private final EpisodeCommentRepository episodeCommentRepository;
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;
    public Long createEpisodeComment(EpisodeCommentDto episodeCommentDto, Long episodeId) { //e북 댓글 생성
        User user = userRepository.findByEmail(episodeCommentDto.getEmail()).orElseThrow(EntityNotFoundException::new);
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(EntityNotFoundException::new);
        episodeCommentRepository.save(EpisodeComment.createEpisodeComment(user, episode, episodeCommentDto));
        episode.addScore(episodeCommentDto.getScore());
        return episode.getEbook().getId();
    }

    @Transactional(readOnly = true)
    public EpisodeComment getEpisodeComment(Long id) { //e북 댓글 조회
        return episodeCommentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteEpisodeComment(Long id) { //e북 댓글 삭제
        episodeCommentRepository.deleteById(id);
    }

    public void updateEpisodeComment(EpisodeComment episodeComment) { //e북 댓글 수정
        episodeCommentRepository.save(episodeComment);
    }

    @Transactional(readOnly = true)
    public List<EpisodeComment> getEpisodeCommentList() { //e북 댓글 조회
        return episodeCommentRepository.findAll();
    }

    public List<EpisodeCommentDto> getEpisodeCommentList(Long userId) {
        return episodeCommentRepository.findByUserId(userId).stream().map(EpisodeComment::of).toList();
    }
}
