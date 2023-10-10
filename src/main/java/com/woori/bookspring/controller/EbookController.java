package com.woori.bookspring.controller;

import com.woori.bookspring.dto.EbookFormDto;
import com.woori.bookspring.service.EbookService;
import com.woori.bookspring.service.LikeService;
import com.woori.bookspring.service.PaginationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller

@RequiredArgsConstructor
public class EbookController {
    private final EbookService ebookService;
    private final LikeService likeService;

    @GetMapping("/ebook") // e북 조회
    public String getEbookList(Model model,
                               @PageableDefault(sort = "regTime", direction = Sort.Direction.DESC) Pageable pageable,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(required = false) String searchType,
                               @RequestParam(required = false) String searchValue) {

        Page<EbookFormDto> ebookList = ebookService.getEbookList(pageable.withPage(page - 1), searchType, searchValue);

        PaginationService.pagination(model, ebookList, page, searchType, searchValue);
        return "ebook/ebookList";
    }

    @GetMapping("/ebook/{ebook-id}") //e북 id 단건 조회
    public String getEbook(@PathVariable("ebook-id") Long ebookId, Model model, Principal principal) {
        String email = "";
        if (principal != null) email = principal.getName();

        EbookFormDto ebook = ebookService.getEbook(ebookId, email);
        if (!email.isBlank()) {
            ebook.setLikeEbookId(likeService.getLikeEbook(ebookId, email));
        }
        model.addAttribute("ebook", ebook);

        return "ebook/ebook";
    }

    @GetMapping("/writer/ebook") //생성 1
    public String ebookForm(Model model) {
        model.addAttribute("ebook", new EbookFormDto());
        return "ebook/ebookForm";
    }

    @PostMapping("/writer/ebook") // 생성 2
    public String createEbook(Model model, @Valid @ModelAttribute("ebook") EbookFormDto ebookFormDto, BindingResult bindingResult, @RequestParam("imgFile") MultipartFile imgFile, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ebook/ebookForm";
        }
        if(imgFile.isEmpty()){
            redirectAttributes.addFlashAttribute("ebook", ebookFormDto);
            throw new RuntimeException("표지를 등록해주세요");
        }
        try {
            ebookService.createEbook(principal.getName(), ebookFormDto, imgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ebook 등록 중 에러가 발생 하였습니다.");
            return "ebook/ebookForm";
        }
        return "redirect:/";
    }

    @DeleteMapping("/writer/ebook/{ebook-id}") //e북 삭제
    public @ResponseBody String deleteEbook(@PathVariable("ebook-id") Long ebookId) {
        ebookService.deleteEbook(ebookId);
        return "e북 삭제";
    }

    @GetMapping("/writer/ebook/{ebook-id}") // 수정 1
    public String updateEbook(@PathVariable("ebook-id") Long ebookId, Model model, Principal principal) {
        EbookFormDto ebookFormDto = ebookService.getEbook(ebookId, principal.getName());
        model.addAttribute("ebook", ebookFormDto);
        return "ebook/ebookForm";
    }

    @PatchMapping("/writer/ebook/{ebook-id}") //e북 수정 2
    public ResponseEntity<?> updateEbook(@PathVariable("ebook-id") Long ebookId, @RequestBody EbookFormDto updatedEbook, @RequestParam("imgFile") MultipartFile imgFile, Model model) {

        try {
            ebookService.updateEbook(ebookId, updatedEbook, imgFile);
        } catch (Exception e) {
            return new ResponseEntity<>("수정 실패", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @GetMapping("/writer")
    public String writerPage(Model model,
                             @PageableDefault(sort = "regTime", direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam(defaultValue = "1") int page, Principal principal){
        Page<EbookFormDto> ebookList = ebookService.getEbookList(pageable.withPage(page - 1), "writer", principal.getName());
        PaginationService.pagination(model, ebookList, page, "writer", principal.getName());
        return "ebook/ebookList";
    }
}
