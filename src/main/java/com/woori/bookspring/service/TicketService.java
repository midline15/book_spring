package com.woori.bookspring.service;

import com.woori.bookspring.config.auth.UserDetailsImpl;
import com.woori.bookspring.dto.TicketDto;
import com.woori.bookspring.entity.Ticket;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.repository.TicketRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public List<TicketDto> getTicketList(String email) {
        return ticketRepository.findByUser_Email(email).stream().map(Ticket::of).toList();
    }

    public void buyTicket(TicketDto ticketDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        user.addTicket(ticketDto.getAmount());
        ticketRepository.save(Ticket.buyTicket(ticketDto, user));
        ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).updateUser(user);
    }
}
