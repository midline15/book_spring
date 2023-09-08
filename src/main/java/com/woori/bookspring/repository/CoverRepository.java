package com.woori.bookspring.repository;

import com.woori.bookspring.entity.Cover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoverRepository extends JpaRepository<Cover, Long> {
}
