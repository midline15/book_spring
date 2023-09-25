package com.woori.bookspring.service;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.dto.EpisodeUserDto;
import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.repository.EbookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class EbookService {

    private final EbookRepository ebookRepository;
    private final CoverService coverService;
    private final EpisodeService episodeService;

    public void createEbook(EbookFormDto ebookFormDto, MultipartFile imgFile) throws Exception {
        Cover cover = coverService.saveCover(imgFile);
        Ebook ebook = ebookFormDto.toEntity(cover);
        ebookRepository.save(ebook);
    }

    @Transactional(readOnly = true)
    public EbookFormDto getEbook(Long ebookId){
        return ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new).of();
    }

    @Transactional(readOnly = true)
    public EbookFormDto getEbook(Long ebookId, String email) {
        // 이북을 찾고
        EbookFormDto ebookFormDto = ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new).of();
        // 해당 이북에서 구입한 에피소드 목록을 찾고
        List<EpisodeUserDto> episodeUserList = episodeService.getEpisodeUserList(email,ebookId);
        episodeUserList.forEach(episodeUserDto -> {
            ebookFormDto.getEpisodeList().forEach(episodeFormDto -> {
                // 이북의 에피소드와 구매한 에피소드를 비교하여
                if(episodeUserDto.getEpisodeId().equals(episodeFormDto.getId())){
                    // 구매 상태를 확인
                    episodeFormDto.checkBuy(true);
                }
            });
        });
        return ebookFormDto;
    }

    @GetMapping
    public void deleteEbook(Long ebookId) { //e북 삭제
        if (!ebookRepository.existsById(ebookId)) {
            throw new IllegalArgumentException("해당 eBook이 존재하지 않슴다.");
        }
        ebookRepository.deleteById(ebookId);
    }

    public void updateEbook(Long id, EbookFormDto updateEbook, MultipartFile imgFile) throws Exception{ //e북 수정
        Ebook ebook = ebookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 eBook을 찾을 수 없슴다."));

        Cover cover = coverService.updateCover(updateEbook.getCoverId(),imgFile);
        ebook.updateEbook(updateEbook, cover);
        ebookRepository.save(ebook);
    }

    @Transactional(readOnly = true)
    public Page<EbookFormDto> getEbookList(Pageable pageable, String type, String keyword) {
        Page<Ebook> ebookList;
        if ("title".equals(type)) {
            ebookList = ebookRepository.findByTitleContaining(pageable, keyword);
        } else if ("intro".equals(type)) {
            ebookList = ebookRepository.findByIntroContaining(pageable, keyword);
        } else {
            ebookList = ebookRepository.findAll(pageable);
        }

        return ebookList.map(Ebook::of);
    }


    public Page<EbookFormDto> getTopEbookList(Pageable pageable) {
        return ebookRepository.findAll(pageable).map(Ebook::of);
    }
}
