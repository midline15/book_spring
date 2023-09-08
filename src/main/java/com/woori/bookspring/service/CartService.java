package com.woori.bookspring.service;

import com.woori.bookspring.dto.AddCartBookDto;
import com.woori.bookspring.dto.CartBookDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Cart;
import com.woori.bookspring.entity.CartBook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.CartBookRepository;
import com.woori.bookspring.repository.CartRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {
    //CRUD
    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public Cart getCart(String username) {
        return cartRepository.findByUser_Username(username).orElseGet(() -> {
            User user = userRepository.findById(username).orElseThrow(EntityNotFoundException::new);
            return createCart(user);
        });
    }

    public List<CartBookDto> getCartBookList(String username) {
        return cartBookRepository.findByCart(getCart(username)).stream().map(CartBook::of).toList();
    }

    public Cart createCart(User user) {
        return cartRepository.save(Cart.createCart(user));
    }

    public void addCart(CartBookDto dto, String username) {
        Cart cart = getCart(username);
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(EntityNotFoundException::new);
        CartBook cartBook = CartBook.createCartBook(book, cart, dto.getCount());
        cartBookRepository.save(cartBook);
    }

    public void deleteCartBook(Long cartBookId) {
        CartBook cartBook = cartBookRepository.findById(cartBookId).orElseThrow(EntityNotFoundException::new);
        cartBookRepository.delete(cartBook);
    }

    public void updateCartBookCount(Long cartBookId, int count) {
        CartBook cartBook = cartBookRepository.findById(cartBookId).orElseThrow(EntityNotFoundException::new);
        cartBook.updateCount(count);
    }

    public void deleteAllCartBooks(Long cartId) {
        cartBookRepository.deleteByCartId(cartId);
    }

    /*public Long orderCartBook(List<CartOrderDto> cartOrderDtoList, String username) {
        List<AddCartBookDto> addCartBookDtoList = new ArrayList<>();
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartBook cartBook = cartBookRepository.findById(cartOrderDto.getCartBookId())
                    .orElseThrow(EntityNotFoundException::new);

            AddCartBookDto addCartBookDto = AddCartBookDto.builder()
                    .bookId(cartBook.getBook().getId())
                    .count(cartBook.getCount())
                    .build();
            addCartBookDtoList.add(addCartBookDto);
        }

        Long orderId = orderService.addOrders(addCartBookDtoList, username);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartBook cartBook = cartBookRepository.findById(cartOrderDto.getCartBookId())
                    .orElseThrow(EntityNotFoundException::new);
            cartBookRepository.delete(cartBook);
        }

        return orderId;
    }*/
    public void orderCartBook(List<CartBookDto> cartBookDtoList, String username) {
        orderService.addOrders(cartBookDtoList, username);
        cartBookDtoList.forEach(cartBookDto -> cartBookRepository.deleteById(cartBookDto.getId()));
    }
}