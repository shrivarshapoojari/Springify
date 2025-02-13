package com.shri.springify.Springify.service;

import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(String jwt, Address shippingAddress, Cart cart) throws Exception;

    Order findOrderById(Long id) throws Exception;

    List<Order> usersOrderHistory(String jwt) throws Exception;

    List<Order> sellersOrder(String jwt) throws Exception;

    Order updateOrderStatus(Long orderId, OrderStatus orderStatus,String jwt) throws Exception;

    Order cancelOrder(Long orderId,String jwt) throws Exception;


}
