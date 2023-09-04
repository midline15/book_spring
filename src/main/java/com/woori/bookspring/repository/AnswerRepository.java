package com.woori.bookspring.repository;

import com.woori.bookspring.entity.qna.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
