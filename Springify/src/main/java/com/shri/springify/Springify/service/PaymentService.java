package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.Order;
import com.shri.springify.Springify.model.PaymentOrder;

import java.util.Set;

public interface PaymentService {

    PaymentOrder createOrder(String jwt, Set<Order> orders);
}
