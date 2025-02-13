package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Order;
import com.shri.springify.Springify.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
}
