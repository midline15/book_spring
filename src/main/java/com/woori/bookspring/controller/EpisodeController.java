package com.woori.bookspring.controller;


import com.woori.bookspring.dto.EpisodeFormDto;
import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    //에피소드 단건 조회
    @GetMapping("/ebook/{ebook-id}/episode/{episode-id}")
    public @ResponseBody String getEpisode(@PathVariable("episode-id") Long epsodeId, Model model) {
        Episode episode = episodeService.getEpisode(epsodeId);
        model.addAttribute("episode", episode);
        return episode.getTitle();
    }
    //에피소드 생성 1
    @GetMapping("/writer/episode")
    public String episodeForm(Model model) {
        model.addAttribute("episode", new EpisodeFormDto());
        return "ebook/episodeForm";
    }

////    에피소드 생성 2
//    @PostMapping(" /writer/episode")
//    public String createEpisode(Model model, @Valid Episode episode, BindingResult ,BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//        return "ebook/episodeForm";
//    }
//        try {
//            episodeService.createEpisode(EpisodeFormDto);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "episode 등록 중 에러가 발생 하였습니다.");
//            return "ebook/episodeForm";
//        }
//        return "redirect:/";
//
//    }

    //에피소드 삭제
    @DeleteMapping(" /writer/episode/{episode-id}")
    public @ResponseBody String deleteEpisode(@PathVariable("episode-id") Long episodeId) {
        episodeService.deleteEpisode(episodeId);
        return "해당 에피소드 삭제 완료";
    }

    @GetMapping(" /writer/episode/{episode-id}")
    public String updateEpisode(@PathVariable("episode-id") Long episodeId, Model model) {
        Episode episode = episodeService.getEpisode(episodeId);
        model.addAttribute("episode", episode);
        return  "ebook/episodeForm";
    }


    //에피소드 수정
    @PatchMapping(" /writer/episode/{episode-id}")
    private ResponseEntity updateEpisode(@PathVariable("episode-id") Long episodeId, @RequestBody Episode episode, Model model) {

        try {
            episodeService.updateEpisode(episode);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "에피소드 수정 중 에러가 발생 하였습니다.");
            return new ResponseEntity<>("수정 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }
}
