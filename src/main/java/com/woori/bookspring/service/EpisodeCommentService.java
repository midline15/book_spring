package com.woori.bookspring.service;

import com.woori.bookspring.entity.ebook.EpisodeComment;
import com.woori.bookspring.repository.EpisodeCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeCommentService {
    private EpisodeCommentRepository episodeCommentRepository;
    public void createEpisodeComment(EpisodeComment episodeComment) { //e북 댓글 생성
        episodeCommentRepository.save(episodeComment);
    }
    public EpisodeComment getEpisodeComment(Long id) { //e북 댓글 조회
        return episodeCommentRepository.findById(id).get();
    }
    public void deleteEpisodeComment(Long id) { //e북 댓글 삭제
        episodeCommentRepository.deleteById(id);
    }
    public void updateEpisodeComment(EpisodeComment episodeComment) { //e북 댓글 수정
        episodeCommentRepository.save(episodeComment);
    }
    public List<EpisodeComment> getEpisodeCommentList() { //e북 댓글 조회
        return episodeCommentRepository.findAll();
    }
}