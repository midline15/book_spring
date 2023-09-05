package com.woori.bookspring.controller;


import com.woori.bookspring.entity.ebook.EBook;
import com.woori.bookspring.entity.ebook.Episode;
import com.woori.bookspring.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("episode")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    //에피소드 전체 조회
    @GetMapping("lists")
    public @ResponseBody  List<Episode> getEpisodeList() {
        return episodeService.getEpisodeList();
    }


    //에피소드 단건 조회
    @GetMapping("{id}")
    public @ResponseBody String getEpisode(@PathVariable Long id, Model model) {
        Episode episode = episodeService.getEpisode(id);
        model.addAttribute("episode", episode);
        return episode.getTitle();
    }

    //에피소드 생성
    @PostMapping("/new")
    public @ResponseBody String createEpisode(@RequestBody Episode episode) {
        return episodeService.createEpisode(episode).getId().toString();
    }

    //에피소드 삭제
    @DeleteMapping("/{id}/delete")
    public @ResponseBody String deleteEpisode(@PathVariable Long id){
        episodeService.deleteEpisode(id);
        return "삭제 완료";
    }

    //에피소드 수정
    @PatchMapping("/{id}/update")
    private @ResponseBody String updateEpisode(@PathVariable Long id, @RequestBody Episode episode) {
        episodeService.updateEpisode(episode);
        return "수정완료";
    }
}