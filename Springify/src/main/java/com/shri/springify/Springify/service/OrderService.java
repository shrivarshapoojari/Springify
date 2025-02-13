package com.shri.springify.Springify.service;

import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(String jwt, Address shippingAddress, Cart cart) throws Exception;

    Order findOrderById(Long id);

    List<Order> usersOrderHistory(String jwt);

    List<Order> sellersOrder(Long sellerId);

    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);

    Order cancelOrder(Long orderId,String jwt);


}
