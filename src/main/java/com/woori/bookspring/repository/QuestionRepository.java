package com.woori.bookspring.repository;

import com.woori.bookspring.entity.qna.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
