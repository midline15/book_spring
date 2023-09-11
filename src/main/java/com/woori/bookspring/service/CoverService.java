package com.woori.bookspring.service;

import com.woori.bookspring.entity.Cover;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.CoverRepository;
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

    @Value("C:/shop/cover")
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

}
