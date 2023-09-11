package com.woori.bookspring.controller;

import com.woori.bookspring.dto.OrderBookDto;
import com.woori.bookspring.dto.OrderDto;
import com.woori.bookspring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequestMapping("order")
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String showOrderList(Model model, Principal principal){
        List<OrderDto> orderDtoList = orderService.getOrderList(principal.getName());
        model.addAttribute("list", orderDtoList);
        return "book/order";
    }

    @PostMapping("{book-id}")
    public String addOrder(OrderBookDto orderBookDto, Principal principal) {
        orderService.addOrder(orderBookDto, principal.getName());
        return "redirect:/order";
    }


}
