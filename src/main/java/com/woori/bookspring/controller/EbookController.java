package com.woori.bookspring.controller;

import com.woori.bookspring.entity.ebook.Ebook;
import com.woori.bookspring.service.EbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("ebook")
@RequiredArgsConstructor
public class EbookController {
    private final EbookService ebookService;

    @GetMapping // e북 조회
    public String getEbookList(Model model) {
        List<Ebook> ebookList = ebookService.getEbookList();
        model.addAttribute("list", ebookList);
        return "ebook/ebookList";
    }

    @GetMapping("{id}") //e북 id 단건 조회
    public String getEbook(@PathVariable Long id, Model model) {
        Ebook ebook = ebookService.getEbook(id);
        model.addAttribute("ebook", ebook);
        return "ebook/ebook";
    }

    @GetMapping("new")
    public String newEbook(){
        return "ebook/ebookForm";
    }
    @PostMapping("new") //e북 생성
    public @ResponseBody String newEbook(@RequestBody Ebook ebook, Model model) {
        return ebookService.createEbook(ebook).getId().toString();
    }

    @DeleteMapping("{id}") //e북 삭제
    public @ResponseBody String deleteEbook(@PathVariable Long id) {
        ebookService.deleteEbook(id);
        return "e북 삭제";
    }

    @PatchMapping("{id}") //e북 수정
    public @ResponseBody String updateEbook(@PathVariable Long id, @RequestBody Ebook Ebook) {
        ebookService.updateEbook(Ebook);
        return "e북 수정";
    }
}
