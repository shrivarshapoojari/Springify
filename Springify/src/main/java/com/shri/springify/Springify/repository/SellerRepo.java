package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<Seller,Long> {

    Seller findByEmail(String email);
}
