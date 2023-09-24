package com.woori.bookspring.repository;

import com.woori.bookspring.dto.TicketDto;
import com.woori.bookspring.entity.Ticket;
import com.woori.bookspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUser_Email(String email);
}
