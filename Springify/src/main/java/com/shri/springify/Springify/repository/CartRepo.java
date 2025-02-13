package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long id);
}
