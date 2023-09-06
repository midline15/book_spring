package com.woori.bookspring.service;

import com.woori.bookspring.entity.board.Article;
import com.woori.bookspring.entity.qna.Question;
import com.woori.bookspring.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    //질문 작성
    @Transactional
    public void createQuestion(Question question) {
        questionRepository.save(question);
    }

    //질문 조회
    public void getQuestion(Long id) {
        questionRepository.findById(id).get();
    }

    //질문 전체 조회
    public List<Question> getQuestionList(){
        return questionRepository.findAll();
    }

    //질문 수정
    @Transactional
    public void updateQuestion(Question question) {
        questionRepository.save(question);
    }

    //질문 삭제
    @Transactional
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
