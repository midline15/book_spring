package com.woori.bookspring.controller;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.dto.SearchParam;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.repository.EbookRepository;
import com.woori.bookspring.service.EbookService;
import com.woori.bookspring.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller

@RequiredArgsConstructor
public class EbookController {
    private final EbookService ebookService;
    private final LikeService likeService;

    @GetMapping("/ebook") // e북 조회
    public String getEbookList(Model model,@RequestParam(value = "searchType", required = false) String searchType, @RequestParam(value = "searchValue", required = false) String searchValue) {

        SearchParam searchParam = new SearchParam();
        searchParam.setSearchType(searchType);
        searchParam.setSearchValue(searchValue);

        List<EbookFormDto> ebookList = ebookService.getEbookList(searchType,searchValue);

        model.addAttribute("list", ebookList);
        model.addAttribute("param", searchParam);
        return "ebook/ebookList";
    }

    @GetMapping("/ebook/{ebook-id}") //e북 id 단건 조회
    public String getEbook(@PathVariable("ebook-id") Long ebookId, Model model, Principal principal) {
        EbookFormDto ebook = ebookService.getEbook(ebookId, principal.getName());
        ebook.setLikeEbookId(likeService.getLikeEbook(ebookId, principal.getName()));        ;
        model.addAttribute("ebook", ebook);
        return "ebook/ebook";
    }

    @GetMapping("/admin/ebook") //생성 1
    public String ebookForm(Model model) {
        model.addAttribute("ebook", new EbookFormDto());
        return "ebook/ebookForm";
    }

    @PostMapping("/admin/ebook") // 생성 2
    public String createEbook(Model model, @Valid EbookFormDto ebookFormDto, BindingResult bindingResult, @RequestParam("imgFile") MultipartFile imgFile) {
        if (bindingResult.hasErrors()) {
            return "ebook/ebookForm";
        }
        try {
            ebookService.createEbook(ebookFormDto, imgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ebook 등록 중 에러가 발생 하였습니다.");
            return "ebook/ebookForm";
        }
        return "redirect:/";
    }

    @DeleteMapping("/admin/ebook/{ebook-id}") //e북 삭제
    public @ResponseBody String deleteEbook(@PathVariable("ebook-id") Long ebookId) {
        ebookService.deleteEbook(ebookId);
        return "e북 삭제";
    }

    @GetMapping("/admin/ebook/{ebook-id}") // 수정 1
    public String updateEbook(@PathVariable("ebook-id") Long ebookId, Model model, Principal principal) {
        EbookFormDto ebookFormDto = ebookService.getEbook(ebookId, principal.getName());
        model.addAttribute("ebook", ebookFormDto);
        return "ebook/ebookForm";
    }

    @PatchMapping("/admin/ebook/{ebook-id}") //e북 수정 2
    public ResponseEntity updateEbook(@PathVariable("ebook-id") Long ebookId, @RequestBody EbookFormDto updatedEbook, @RequestParam("imgFile") MultipartFile imgFile, Model model) {

        try {
            ebookService.updateEbook(ebookId, updatedEbook, imgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생 하였습니다.");
            return new ResponseEntity<>("수정 실패", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }
}
