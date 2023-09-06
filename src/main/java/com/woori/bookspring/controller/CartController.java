package com.woori.bookspring.controller;

import com.woori.bookspring.entity.Cart;
import com.woori.bookspring.entity.CartBook;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("cart")
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String showCart(Model model, User user){
        Cart cart = cartService.getCart(user);
        List<CartBook> cartBookList = cartService.getCartBookList(cart);
        model.addAttribute("list", cartBookList);
        return "cartList";
    }
}
