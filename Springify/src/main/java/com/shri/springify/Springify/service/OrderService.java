package com.shri.springify.Springify.service;

import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.model.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(String jwt, Address shippingAddress) throws Exception;

    Order findOrderById(Long id,String jwt) throws Exception;

    List<Order> usersOrderHistory(String jwt) throws Exception;

    List<Order> sellersOrder(String jwt) throws Exception;

    Order updateOrderStatus(Long orderId, OrderStatus orderStatus,String jwt) throws Exception;

    Order cancelOrder(Long orderId,String jwt) throws Exception;

    OrderItem findOrderItemById(Long orderItemId,String jwt) throws Exception;
}
