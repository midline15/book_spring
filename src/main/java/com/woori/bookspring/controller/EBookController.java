package com.woori.bookspring.controller;

import com.woori.bookspring.entity.ebook.EBook;
import com.woori.bookspring.service.EBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("ebook")
@RequiredArgsConstructor
public class EBookController {
    private final EBookService eBookService;

    @GetMapping("/lists") // e북 조회
    public @ResponseBody List<EBook> getEBookList(Model model) {
        return eBookService.getEBookList();
    }

    @GetMapping("/{id}") //e북 id 단건 조회
    public @ResponseBody Long getEBook(@PathVariable Long id, Model model) {
        EBook ebook = eBookService.getEBook(id);
        model.addAttribute("ebook", ebook);
        return ebook.getId();
    }

    @PostMapping("/new") //e북 생성
    public @ResponseBody String newEBook(@RequestBody EBook eBook, Model model) {
        return eBookService.createEBook(eBook).getId().toString();
    }

    @DeleteMapping("{id}/delete") //e북 삭제
    public @ResponseBody String deleteEBook(@PathVariable Long id, EBook eBook) {
        eBookService.deleteEBook(id);
        return "e북 삭제";
    }

    @PatchMapping("/{id}/create") //e북 수정
    public @ResponseBody String updateEBook(@PathVariable Long id, @RequestBody EBook eBook) {
        eBookService.updateEBook(eBook);
        return "e북 수정";
    }
}
