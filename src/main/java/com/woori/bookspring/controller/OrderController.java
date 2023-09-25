package com.woori.bookspring.controller;

import com.woori.bookspring.config.auth.UserDetailsImpl;
import com.woori.bookspring.constant.Role;
import com.woori.bookspring.dto.OrderBookDto;
import com.woori.bookspring.dto.OrderDto;
import com.woori.bookspring.entity.User;
import com.woori.bookspring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> addOrder(OrderBookDto orderBookDto, Principal principal) {
        User user = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if(!user.getRole().equals(Role.USER)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        orderService.addOrder(orderBookDto, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{order-id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("order-id") Long orderId){
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>("주문 취소 완료", HttpStatus.OK);
    }
}
