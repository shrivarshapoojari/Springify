package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {


    List<Order> findByUserId(Long userId);
    List<Order> findBySellerId(Long sellerId);
}
