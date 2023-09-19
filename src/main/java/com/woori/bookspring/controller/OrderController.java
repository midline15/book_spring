package com.woori.bookspring.controller;

import com.woori.bookspring.dto.OrderBookDto;
import com.woori.bookspring.dto.OrderDto;
import com.woori.bookspring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "user/order";
    }

    @PostMapping("{book-id}")
    public String addOrder(OrderBookDto orderBookDto, Principal principal) {
        orderService.addOrder(orderBookDto, principal.getName());
        return "redirect:/order";
    }

    @PatchMapping("{order-id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("order-id") Long orderId){
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>("주문 취소 완료", HttpStatus.OK);
    }
}
