package com.woori.bookspring.service;

import com.woori.bookspring.dto.CartBookDto;
import com.woori.bookspring.dto.OrderBookDto;
import com.woori.bookspring.dto.OrderDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Order;
import com.woori.bookspring.entity.OrderBook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.OrderBookRepository;
import com.woori.bookspring.repository.OrderRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    public final OrderRepository orderRepository;
    public final OrderBookRepository orderBookRepository;
    public final UserRepository userRepository;
    public final BookRepository bookRepository;

    @Transactional(readOnly = true) // 주문 내역 조회
    public List<OrderDto> getOrderList(String username) {
        return orderRepository.findByUser_Username(username).stream().map(Order::of).toList();
    }

    public Order createOrder(String username){
        User user = userRepository.findById(username).orElseThrow(EntityNotFoundException::new);
        return orderRepository.save(Order.createOrder(user));
    }
    public void createOrderBook(OrderBookDto orderBookDto, Book book, Order order){
        OrderBook orderBook = OrderBook.createOrderBook(orderBookDto,book, order);
        orderBookRepository.save(orderBook);
    }
    // 단건 주문
    public void addOrder(OrderBookDto orderBookDto, String username){
        Order order = createOrder(username);
        Book book = bookRepository.findById(orderBookDto.getBookId()).orElseThrow(EntityNotFoundException::new);
        createOrderBook(orderBookDto, book, order);
    }

    //장바구니에서 주문
    /*public Long addOrders(List<AddCartBookDto> addCartBookDtoList, String username) {
        User user = userRepository.findById(username).get();
        Order order = Order.createOrder(user);
        orderRepository.save(order);

        for (AddCartBookDto addCartBookDto : addCartBookDtoList) {
            Book book = bookRepository.findById(addCartBookDto.getBookId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderBookDto orderBookDto = OrderBookDto.createOrderBookDto(book, addCartBookDto);

            OrderBook orderBook = OrderBook.createOrderBook(orderBookDto, book, order);
            orderBookRepository.save(orderBook);
        }

        return order.getId();
    }*/
    public void addOrders(List<CartBookDto> cartBookDtoList, String username) {
        Order order = createOrder(username);
        cartBookDtoList.forEach(cartBookDto -> {
            Book book = bookRepository.findById(cartBookDto.getBookId()).orElseThrow(EntityNotFoundException::new);
            createOrderBook(cartBookDto.toOrderBook(), book, order);
        });
    }

    public void deleteOrder(Long orderId){
        orderRepository.deleteById(orderId);
    }
}
