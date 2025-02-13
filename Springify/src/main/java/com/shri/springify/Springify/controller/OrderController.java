package com.shri.springify.Springify.controller;


import com.shri.springify.Springify.domain.PaymentMethod;
import com.shri.springify.Springify.model.*;
import com.shri.springify.Springify.response.PaymentLinkResponse;
import com.shri.springify.Springify.service.OrderService;
import com.shri.springify.Springify.service.PaymentService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrderController {



    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/buy")
    public ResponseEntity<PaymentLinkResponse> createOrder(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt
            ) throws Exception
    {

        Set<Order>orders=orderService.createOrder(jwt,shippingAddress);
        PaymentOrder paymentOrder=paymentService.createOrder(jwt,orders);
        return  null;
    }


    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {


        List<Order> orders = orderService.usersOrderHistory(jwt);


        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @GetMapping("/{orderId}")

    public ResponseEntity<Order> getOrderById(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return  new ResponseEntity<>(orderService.findOrderById(orderId,jwt),HttpStatus.OK);
    }

    @GetMapping("/item/{orderItemId}")
    public  ResponseEntity<OrderItem> getOrderItemById(
            @PathVariable Long orderItemId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return new ResponseEntity<>(orderService.findOrderItemById(orderItemId,jwt),HttpStatus.OK);
    }




    @PutMapping("/{orderId}/cancel")
    public  ResponseEntity<Order> cancelOrder(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return new ResponseEntity<>(orderService.cancelOrder(orderId,jwt),HttpStatus.OK);
    }


}
