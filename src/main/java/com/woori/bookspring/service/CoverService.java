package com.woori.bookspring.service;

import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.CoverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CoverService {

    @Value("${imgLocation}")
    private String imgLocation;
    private final BookRepository bookRepository;
    private final CoverRepository coverRepository;
    private final FileService fileService;

    public Cover saveCover(MultipartFile imgFile) throws Exception{
        String origName = imgFile.getOriginalFilename();
        String name = "";
        String url = "";

        if(!StringUtils.isEmpty(origName)){
            name = fileService.uploadFile(imgLocation, origName, imgFile.getBytes());
            url = "/images/cover/" + name;
        }
        Cover cover = new Cover().updateCover(origName, name, url);
        return coverRepository.save(cover);
    }

    public Cover updateCover(Long coverId, MultipartFile imgFile) throws Exception{

        if (!imgFile.isEmpty()) {
            Cover findCover = coverRepository.findById(coverId)
                    .orElseThrow(EntityNotFoundException::new);
            //기존 이미지 삭제 파일
            if (!StringUtils.isEmpty(findCover.getName())) {
                fileService.deleteFile(imgLocation + "/" + findCover.getName());
            }
            String origName = imgFile.getOriginalFilename();
            String name = fileService.uploadFile(imgLocation, origName, imgFile.getBytes());
            String url = "/images/cover/" + name;
            return findCover.updateCover(origName, name, url);
        }
        return null;
    }
}
