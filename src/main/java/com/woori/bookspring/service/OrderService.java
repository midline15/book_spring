package com.woori.bookspring.service;

import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Order;
import com.woori.bookspring.entity.OrderBook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Member;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    public final OrderRepository orderRepository;

    public final OrderBookRepository orderBookRepository;

    public final UserRepository userRepository;

    public final BookRepository bookRepository;
    // bookRepository .. 필요한지?..

    @Transactional // order 목록 조회
    public List<Order> getOrderList(User user) {
        return orderRepository.findByUser(user);
    }

    // 단건 주문
    public void addOrder(Book book,int count, User user){

        // User 엔티티를 사용하여 Order 엔티티 생성
        Order newOrder = Order.builder()
                .user(user).build(); // User 엔티티를 주문에 연결하기

        orderRepository.save(newOrder); // 주문 생성

        // 주문상품 생성
        OrderBook orderBook = OrderBook.builder()
                .count(count)
                .totalPrice(book.getPrice()*count)
                .book(book)
                .order(newOrder) // 주문상품에 주문 정보를 연결
                .build();
        orderBookRepository.save(orderBook); // 주문상품 저장
    }


    // 삭제
    @Transactional
    public void deleteOrder(Long orderId){
        orderRepository.deleteById(orderId);
    }
}
