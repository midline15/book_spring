package com.woori.bookspring.service;

import com.woori.bookspring.entity.qna.Answer;
import com.woori.bookspring.repository.AnswerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    //답변 작성
    @Transactional
    public void createAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    //답변 조회
    public void getAnswer(Long id) {
        answerRepository.findById(id).get();
    }

    //답변 수정
    @Transactional
    public void updateAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    //답변 삭제
    @Transactional
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
