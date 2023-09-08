package com.woori.bookspring.service;

import com.woori.bookspring.entity.qna.Answer;
import com.woori.bookspring.repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void createAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public void getAnswer(Long id) {
        answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void updateAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
