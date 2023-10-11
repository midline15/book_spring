package com.woori.bookspring.controller;


import com.woori.bookspring.dto.EpisodeCommentDto;
import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.service.EbookService;
import com.woori.bookspring.service.EpisodeCommentService;
import com.woori.bookspring.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;
    private final EpisodeCommentService episodeCommentService;

    //에피소드 단건 조회
    @GetMapping("/ebook/{ebook-id}/episode/{episode-id}")
    public String getEpisode(@PathVariable("episode-id") Long episodeId, Model model) {
        System.out.println(episodeService.checkBuy(episodeId));
        if(!episodeService.checkBuy(episodeId)){
            throw new RuntimeException("구매 후 이용해 주세요.");
        }
        EpisodeFormDto episode = episodeService.getEpisode(episodeId);
        model.addAttribute("episode", episode);
        return "ebook/episode";
    }
    //에피소드 생성 1
    @GetMapping("/writer/ebook/{ebook-id}/episode")
    public String episodeForm(@PathVariable("ebook-id") Long ebookId, Model model) {
        model.addAttribute("episode", EpisodeFormDto.builder().ebookId(ebookId).build());
        return "ebook/episodeForm";
    }

//    에피소드 생성 2
    @PostMapping("/writer/ebook/{ebook-id}/episode")
    public String createEpisode(Model model, @Valid EpisodeFormDto episodeFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        return "ebook/episodeForm";
    }
        try {
            episodeService.createEpisode(episodeFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "episode 등록 중 에러가 발생 하였습니다.");
            return "ebook/episodeForm";
        }
        return "redirect:/ebook/"+episodeFormDto.getEbookId();

    }

    //에피소드 삭제
    @DeleteMapping("/writer/ebook/{ebook-id}/episode/{episode-id}")
    public @ResponseBody String deleteEpisode(@PathVariable("episode-id") Long episodeId) {
        episodeService.deleteEpisode(episodeId);
        return "해당 에피소드 삭제 완료";
    }

    @GetMapping("/writer/ebook/{ebook-id}/episode/{episode-id}/update")
    public String updateEpisode(@PathVariable("episode-id") Long episodeId, Model model) {
        EpisodeFormDto episode = episodeService.getEpisode(episodeId);
        model.addAttribute("episode", episode);
        return  "ebook/episodeForm";
    }


    //에피소드 수정
    @PatchMapping("/writer/ebook/{ebook-id}/episode/{episode-id}")
    public ResponseEntity updateEpisode(@RequestBody EpisodeFormDto episodeFormDto, Model model) {

        try {
            episodeService.updateEpisode(episodeFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "에피소드 수정 중 에러가 발생 하였습니다.");
            return new ResponseEntity<>("수정 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @PostMapping("/ebook/{ebook-id}/episode/{episode-id}")
    public ResponseEntity<?> buyEpisode(@PathVariable("episode-id") Long episodeId, Principal principal){
        if(!episodeService.buyEpisode(episodeId, principal.getName())) {
            throw new RuntimeException("이용권이 부족합니다.");
        }

        return new ResponseEntity<>("구매 완료", HttpStatus.OK);
    }

    @PostMapping("/episode/{episode-id}/episode-comment")
    public String createEpisodeComment(EpisodeCommentDto episodeCommentDto, @PathVariable("episode-id") Long episodeId){
        Long ebookId = episodeCommentService.createEpisodeComment(episodeCommentDto, episodeId);
        return "redirect:/ebook/"+ebookId+"/episode/{episode-id}";
    }
}
