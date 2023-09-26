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

        int totalPage = ebookList.getTotalPages();

        PaginationService paging = new PaginationService();

        model.addAttribute("list", ebookList);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, totalPage));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        return "ebook/ebookList";
    }

    @GetMapping("/ebook/{ebook-id}") //e북 id 단건 조회
    public String getEbook(@PathVariable("ebook-id") Long ebookId, Model model, Principal principal) {
        EbookFormDto ebook = ebookService.getEbook(ebookId, principal.getName());
        if(!principal.getName().isBlank()){
            ebook.setLikeEbookId(likeService.getLikeEbook(ebookId, principal.getName()));
        }
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
