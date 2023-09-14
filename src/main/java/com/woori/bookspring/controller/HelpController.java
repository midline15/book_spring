package com.woori.bookspring.controller;

import com.woori.bookspring.constant.ArticleType;
import com.woori.bookspring.dto.ArticleDto;
import com.woori.bookspring.dto.CommentDto;
import com.woori.bookspring.dto.HelpFormDto;
import com.woori.bookspring.service.ArticleService;
import com.woori.bookspring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HelpController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping("article/{article-type}")
    public String getNoticeList(Model model, @PathVariable("article-type") String articleType){
        List<ArticleDto> noticeList = articleService.getArticleList(ArticleType.getArticleType(articleType));
        model.addAttribute("list",noticeList);
        return "help/noticeList";
    }

    @GetMapping("article/03/{article-id}")
    public String getNotice(@PathVariable("article-id") Long articleId, Model model){
        ArticleDto notice = articleService.getArticle(articleId);
        model.addAttribute("article", notice);
        return "help/notice";
    }

    @GetMapping("article/03/new")
    public String createNotice(@ModelAttribute("article") HelpFormDto helpFormDto){
        return "help/noticeForm";
    }

    @PostMapping("article/03")
    public String createNotice(HelpFormDto dto, Principal principal) {
        articleService.createHelpArticle(dto, principal.getName());
        return "redirect:/article/03";
    }

    @PatchMapping("article/03/{article-id}")
    public ResponseEntity<?> updateNotice(HelpFormDto dto){
        articleService.updateArticle(dto);
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("article/03/{article-id}")
    public ResponseEntity<?> deleteNotice(@PathVariable("article-id") Long articleId){
        articleService.deleteArticle(articleId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @GetMapping("article/04")
    public String getEventList(Model model){
        List<ArticleDto> eventList = articleService.getArticleList(ArticleType.EVENT);
        model.addAttribute("list",eventList);
        return "help/eventList";
    }

    @GetMapping("article/04/{article-id}")
    public String getEvent(@PathVariable("article-id") Long articleId, Model model){
        ArticleDto event = articleService.getArticle(articleId);
        model.addAttribute("article", event);
        return "help/event";
    }

    @GetMapping("article/04/new")
    public String createEvent(@ModelAttribute("article") HelpFormDto helpFormDto){
        return "help/evnetForm";
    }

    @PostMapping("article/04")
    public String createEvent(HelpFormDto dto, Principal principal) {
        articleService.createHelpArticle(dto, principal.getName());
        return "redirect:/article/04";
    }

    @PatchMapping("article/04/{article-id}")
    public ResponseEntity<?> updateEvent(HelpFormDto dto){
        articleService.updateArticle(dto);
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("article/04/{article-id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("article-id") Long articleId){
        articleService.deleteArticle(articleId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @GetMapping("article/05")
    public String getQuestionList(Model model){
        List<ArticleDto> questionList = articleService.getArticleList(ArticleType.QUESTION);
        model.addAttribute("list",questionList);
        return "help/questionList";
    }

    @GetMapping("article/05/{article-id}")
    public String getQuestion(@PathVariable("article-id") Long articleId, Model model){
        ArticleDto Question = articleService.getArticle(articleId);
        model.addAttribute("article", Question);
        return "help/question";
    }

    @GetMapping("article/05/new")
    public String createQuestion(@ModelAttribute("article") HelpFormDto helpFormDto){
        return "help/QuestionForm";
    }

    @PostMapping("article/05")
    public String createQuestion(HelpFormDto dto, Principal principal) {
        articleService.createHelpArticle(dto, principal.getName());
        return "redirect:/article/05";
    }

    @PatchMapping("article/05/{article-id}")
    public ResponseEntity<?> updateQuestion(HelpFormDto dto){
        articleService.updateArticle(dto);
        return new ResponseEntity<>("수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("article/05/{article-id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("article-id") Long articleId){
        articleService.deleteArticle(articleId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @PostMapping("article/05/{article-id}/comment")
    public String createAnswer(@PathVariable("article-id") Long articleId, CommentDto commentDto){
        commentService.createComment(articleId, commentDto);

        return "redirect:/article/05/{article-id}";
    }

    @PatchMapping("article/05/{article-id}/comment/{comment-id}")
    public ResponseEntity<?> updateAnswer(CommentDto commentDto){
        commentService.updateComment(commentDto);
        return new ResponseEntity<>("답변 수정 완료", HttpStatus.OK);
    }
}
