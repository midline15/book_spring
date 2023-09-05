package com.woori.bookspring.service;

import com.woori.bookspring.entity.ebook.EBook;
import com.woori.bookspring.repository.EBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class EBookService {
    //1234
    private final EBookRepository eBookRepository;

    public EBook createEBook(EBook eBook) { //e북 생성
        return eBookRepository.save(eBook);
    }

    public EBook getEBook(Long id) { //e북 조회, 검색
        return eBookRepository.findById(id).get();
    }

    public void deleteEBook(Long id) { //e북 삭제
        eBookRepository.deleteById(id);
    }

    public void updateEBook(EBook eBook) { //e북 수정
        eBookRepository.save(eBook);
    }

    public List<EBook> getEBookList() { //e북 리스트,목록
        return eBookRepository.findAll();
    }
}
