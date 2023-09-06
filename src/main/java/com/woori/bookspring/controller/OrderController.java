package com.woori.bookspring.controller;

import com.woori.bookspring.entity.Order;
import com.woori.bookspring.entity.user.User;
import com.woori.bookspring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("order")
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String showOrderList(Model model, User user){
        List<Order> orderList = orderService.getOrderList(user);
        model.addAttribute("list", orderList);
        return "book/order";
    }
}
