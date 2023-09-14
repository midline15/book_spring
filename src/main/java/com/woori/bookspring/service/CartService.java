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
import org.thymeleaf.util.StringUtils;

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

    public Cart getCart(String email) {
        return cartRepository.findByUser_Email(email).orElseGet(() -> {
            User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
            return createCart(user);
        });
    }

    public List<CartBookDto> getCartBookList(String email) {
        return cartBookRepository.findByCart(getCart(email)).stream().map(CartBook::of).toList();
    }

    public Cart createCart(User user) {
        return cartRepository.save(Cart.createCart(user));
    }

    public void addCart(CartBookDto dto, String email) {
        Cart cart = getCart(email);
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(EntityNotFoundException::new);
        CartBook cartBook = CartBook.createCartBook(book, cart, dto.getCount());
        cartBookRepository.save(cartBook);
    }

    public void deleteCartBook(Long cartBookId) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);
        cartBookRepository.delete(cartBook);
    }

    public void updateCartBookCount(Long cartBookId, int count) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);
        cartBook.updateCount(count);
    }

    public void deleteAllCartBooks(Long cartId) {
        cartBookRepository.deleteByCartId(cartId);
    }


    public void orderCartBook(List<CartBookDto> cartBookDtoList, String email) {
        orderService.addOrders(cartBookDtoList, email);
        cartBookDtoList.forEach(cartBookDto -> cartBookRepository.deleteById(cartBookDto.getId()));
    }

    @Transactional
    public boolean validateCartBook(Long cartBookId, String email) {
        User user = userRepository.findByEmail(email).get();
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);
        User savedUser = cartBook.getCart().getUser();

        if (!StringUtils.equals(user.getEmail(),
                savedUser.getEmail())){
            return false;
        }
        return true;
    }
}