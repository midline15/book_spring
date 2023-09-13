package com.woori.bookspring.controller;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.repository.EbookRepository;
import com.woori.bookspring.service.EbookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller

@RequiredArgsConstructor
public class EbookController {
    private final EbookService ebookService;
    private EbookRepository ebookRepository;

    @GetMapping("/ebook") // e북 조회
    public String getEbookList(Model model) {
        List<Ebook> ebookList = ebookService.getEbookList();
        model.addAttribute("list", ebookList);
        return "ebook/ebookList";
    }

    @GetMapping("/ebook/{id}") //e북 id 단건 조회
    public String getEbook(@PathVariable Long id, Model model) {
        EbookFormDto ebook = ebookService.getEbook(id);
        model.addAttribute("ebook", ebook);
        return "ebook/ebook";
    }

    @GetMapping("/admin/ebook/new") //생성 1
    public String ebookFrom(Model model) {
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

    @DeleteMapping("/admin/ebook/{id}") //e북 삭제
    public @ResponseBody String deleteEbook(@PathVariable Long id) {
        ebookService.deleteEbook(id);
        return "e북 삭제";
    }

    @GetMapping("/admin/ebook/{id}") // 수정 1
    public String updateEbook(@PathVariable Long id, Model model) {
        EbookFormDto ebookFormDto = ebookService.getEbook(id);
        model.addAttribute("ebook", ebookFormDto);
        return "ebook/ebookForm";
    }

    @PatchMapping("/admin/ebook/{id}") //e북 수정 2
    public ResponseEntity updateEbook(@PathVariable Long id, @RequestBody EbookFormDto updatedEbook, @RequestParam("imgFile") MultipartFile imgFile, Model model) {

        try {
            ebookService.updateEbook(id, updatedEbook, imgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생 하였습니다.");
            return new ResponseEntity<>("수정 실패", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }
}
