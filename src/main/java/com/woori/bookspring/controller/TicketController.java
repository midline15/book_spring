package com.woori.bookspring.controller;

import com.woori.bookspring.dto.TicketDto;
import com.woori.bookspring.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("ticket")
    public String getTicketList(Model model, Principal principal){
        model.addAttribute("list", ticketService.getTicketList(principal.getName()));
        return "user/ticketList";
    }

    @GetMapping("ticket/buy")
    public String ticketPage(){
        return "user/ticket";
    }

    @PostMapping("ticket/buy")
    public String buyTicket(@RequestBody TicketDto ticketDto, Principal principal){
        ticketService.buyTicket(ticketDto, principal.getName());
        return "redirect:/ticket";
    }
}
