package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {
}
