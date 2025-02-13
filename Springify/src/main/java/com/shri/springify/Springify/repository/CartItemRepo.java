package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Cart;
import com.shri.springify.Springify.model.CartItem;
import com.shri.springify.Springify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
