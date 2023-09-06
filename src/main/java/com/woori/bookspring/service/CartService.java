package com.woori.bookspring.service;

import com.woori.bookspring.entity.Book;
import com.woori.bookspring.entity.Cart;
import com.woori.bookspring.entity.CartBook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.repository.BookRepository;
import com.woori.bookspring.repository.CartBookRepository;
import com.woori.bookspring.repository.CartRepository;
import com.woori.bookspring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {
    //CRUD
    private final CartRepository cartRepository;

    private final CartBookRepository cartBookRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    // 조회
    @Transactional
    public Cart getCart(User user) {
        return cartRepository.findByUser(user);
    }


    @Transactional
    public List<CartBook> getCartBookList(Cart cart) {
        return cartBookRepository.findByCart(cart);
    }

    @Transactional
    public void createCart(User user) {
        Cart cart = Cart.builder()
                .user(user)
                .build();
        cartRepository.save(cart);
    }


    // 추가
    @Transactional
    public void addCart(Book book, int count, User user) {
        Cart cart = cartRepository.findByUser(user);
        // 장바구니에 담을 상품 엔티티 조회
        CartBook cartBook = CartBook.builder().book(book).count(count).cart(cart).build();

        cartBookRepository.save(cartBook); // 장바구니에 들어갈 상품 저장

    }

    // 상품정보의 x 버튼 클릭하면 장바구니에 넣어 놓은 상품을 삭제
    public void deleteCartBook(Long cartBookId) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);
        cartBookRepository.delete(cartBook);
    }


    // 장바구니 상품의 수량 수정
    public void updateCartBookCount(Long cartBookId, int count) {
        CartBook cartBook = cartBookRepository.findById(cartBookId)
                .orElseThrow(EntityNotFoundException::new);

        cartBook.updateCount(count);
    }

    public void deleteAllCartBooks(Long cartId) {
        cartBookRepository.deleteByCartId(cartId);
    }
}