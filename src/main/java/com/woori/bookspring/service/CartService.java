package com.woori.bookspring.service;

import com.woori.bookspring.dto.CartBookDto;
import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Cart;
import com.woori.bookspring.entity.CartBook;
import com.woori.bookspring.entity.User;
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


    public void orderCartBook(List<CartBookDto> cartBookDtoList, String username) {
        orderService.addOrders(cartBookDtoList, username);
        cartBookDtoList.forEach(cartBookDto -> cartBookRepository.deleteById(cartBookDto.getId()));
    }
}