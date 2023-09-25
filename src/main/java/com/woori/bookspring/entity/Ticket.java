package com.woori.bookspring.entity;

import com.woori.bookspring.dto.TicketDto;
import com.woori.bookspring.entity.base.BaseEntity;
import com.woori.bookspring.entity.ebook.Episode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    private String history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Ticket buyTicket(TicketDto ticketDto, User user) {
        return Ticket.builder()
                .amount(ticketDto.getAmount())
                .history(ticketDto.getHistory())
                .user(user)
                .build();
    }

    public static Ticket useTicket(Episode episode, User user) {
        return Ticket.builder()
                .amount(-episode.getEbook().getPrice())
                .history(episode.getTitle() + " 구매")
                .user(user)
                .build();
    }

    public TicketDto of() {
        return TicketDto.builder()
                .amount(amount)
                .history(history)
                .regTime(getRegTime())
                .build();
    }
}
