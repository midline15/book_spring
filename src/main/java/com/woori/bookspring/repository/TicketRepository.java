package com.woori.bookspring.repository;

import com.woori.bookspring.entity.ebook.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
