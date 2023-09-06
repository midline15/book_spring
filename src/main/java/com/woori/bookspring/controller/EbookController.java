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
    private final EbookService eBookService;

    @GetMapping // e북 조회
    public @ResponseBody List<Ebook> getEBookList(Model model) {
        return eBookService.getEBookList();
    }

    @GetMapping("{id}") //e북 id 단건 조회
    public @ResponseBody Long getEBook(@PathVariable Long id, Model model) {
        Ebook ebook = eBookService.getEBook(id);
        model.addAttribute("ebook", ebook);
        return ebook.getId();
    }

    @GetMapping("new")
    public String newEbook(){
        return "ebook/ebookForm";
    }
    @PostMapping("new") //e북 생성
    public @ResponseBody String newEBook(@RequestBody Ebook eBook, Model model) {
        return eBookService.createEBook(eBook).getId().toString();
    }

    @DeleteMapping("{id}") //e북 삭제
    public @ResponseBody String deleteEBook(@PathVariable Long id, Ebook eBook) {
        eBookService.deleteEBook(id);
        return "e북 삭제";
    }

    @PatchMapping("{id}") //e북 수정
    public @ResponseBody String updateEBook(@PathVariable Long id, @RequestBody Ebook eBook) {
        eBookService.updateEBook(eBook);
        return "e북 수정";
    }
}
