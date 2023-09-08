package com.woori.bookspring.service;

import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.repository.EbookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class EbookService {

    private final EbookRepository ebookRepository;

    public Ebook createEbook(Ebook ebook) { //e북 생성
        return ebookRepository.save(ebook);
    }

    @Transactional(readOnly = true)
    public Ebook getEbook(Long ebookId) { //e북 조회, 검색
        return ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new);
    }
    public void deleteEbook(Long ebookId) { //e북 삭제
        ebookRepository.deleteById(ebookId);
    }

    public void updateEbook(Ebook ebook) { //e북 수정
        ebookRepository.save(ebook);
    }

    @Transactional(readOnly = true)
    public List<Ebook> getEbookList() { //e북 리스트,목록
        return ebookRepository.findAll();
    }
}
