package com.woori.bookspring.service;

import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.qna.Question;
import com.woori.bookspring.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void createQuestion(Question question) {
        questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public void getQuestion(Long id) {
        questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionList(){
        return questionRepository.findAll();
    }

    public void updateQuestion(Question question) {
        questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
