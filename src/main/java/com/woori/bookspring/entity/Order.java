package com.woori.bookspring.entity;

import com.woori.bookspring.constant.OrderStatus;
import com.woori.bookspring.dto.OrderDto;
import com.woori.bookspring.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderBook> orderBookList;

    private int orderPrice;

    public int calculate() {
        int orderPrice = 0;
        for (OrderBook orderBook : orderBookList) {
            orderPrice += orderBook.getTotalPrice();
        }
        return orderPrice;
    }

    public OrderDto of() {
        return OrderDto.builder()
                .id(id)
                .orderBookDtoList(orderBookList.stream().map(OrderBook::of).toList())
                .orderPrice(calculate())
                .regTime(getRegTime())
                .orderStatus(orderStatus)
                .build();
    }

    public static Order createOrder(User user) {
        return Order.builder()
                .user(user)
                .orderStatus(OrderStatus.ORDER)
                .orderBookList(new ArrayList<OrderBook>())
                .build();
    }

    public void addOrderBook(OrderBook orderBook) {
        orderBookList.add(orderBook);
        orderBook.setOrder(this);
    }
}