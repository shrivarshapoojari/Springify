package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.model.Order;
import com.shri.springify.Springify.service.OrderService;
import com.shri.springify.Springify.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private SellerService sellerService;



    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return new ResponseEntity<>(orderService.sellersOrder(jwt), HttpStatus.OK);
    }



    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @PathVariable OrderStatus orderStatus
            ) throws Exception {

        return  new ResponseEntity<>(orderService.updateOrderStatus(orderId,orderStatus,jwt),HttpStatus.OK);
    }
}
