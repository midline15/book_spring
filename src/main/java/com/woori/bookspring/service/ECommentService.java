package com.woori.bookspring.service;

import com.woori.bookspring.entity.ebook.EComment;
import com.woori.bookspring.repository.EBookRepository;
import com.woori.bookspring.repository.ECommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class ECommentService {
    private ECommentRepository eCommentRepository;
    public void createEComment(EComment eComment) { //e북 댓글 생성
        eCommentRepository.save(eComment);
    }
    public EComment getEComment(Long id) { //e북 댓글 조회
        return eCommentRepository.findById(id).get();
    }
    public void deleteEComment(Long id) { //e북 댓글 삭제
        eCommentRepository.deleteById(id);
    }
    public void updateEComment(EComment eComment) { //e북 댓글 수정
        eCommentRepository.save(eComment);
    }
    public List<EComment> getECommentList() { //e북 댓글 조회
        return eCommentRepository.findAll();
    }
}
