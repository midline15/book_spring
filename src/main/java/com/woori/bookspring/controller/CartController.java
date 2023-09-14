package com.woori.bookspring.controller;

import com.woori.bookspring.dto.*;
import com.woori.bookspring.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("cart")
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String showCart(Model model, Principal principal){
        List<CartBookDto> cartBookDtoList = cartService.getCartBookList(principal.getName());
        model.addAttribute("list", cartBookDtoList);
        return "book/cart";
    }

    @PostMapping("{book-id}")
    public @ResponseBody ResponseDto<?> addCartBook(@RequestBody CartBookDto dto, Principal principal){
        cartService.addCart(dto, principal.getName());
        return new ResponseDto<>(HttpStatus.OK.value(), "장바구니에 등록 되었습니다.");
    }

    @DeleteMapping("{cart-book-id}")
    public @ResponseBody ResponseEntity<?> deleteCartBook
            (@PathVariable("cart-book-id") Long cartBookId) {
        cartService.deleteCartBook(cartBookId);
        return new ResponseEntity<>(cartBookId, HttpStatus.OK);
    }

    @PostMapping("order")
    public ResponseEntity<?> orderCartBook(@RequestBody CartOrderDto cartOrderDto, Principal principal) {

        List<CartBookDto> cartBookDtoList = cartOrderDto.getCartBookDtoList();

        if (cartBookDtoList == null || cartBookDtoList.isEmpty()) {
            return new ResponseEntity<>("주문할 상품을 선택해주세요.", HttpStatus.FORBIDDEN);
        }

        cartService.orderCartBook(cartBookDtoList, principal.getName());

        return new ResponseEntity<>("주문 완료", HttpStatus.OK);
    }

    @PatchMapping("{cart-book-id}")
    public @ResponseBody ResponseEntity updateCartBook
            (@PathVariable("cart-book-id") Long id, int count, Principal principal){

        if (count <= 0){
            return new ResponseEntity<String>
                    ("최소 1개 이상 담아주세요.",HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartBook
                (id, principal.getName())){
            return new ResponseEntity<String>
                    ("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.updateCartBookCount(id,count);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
