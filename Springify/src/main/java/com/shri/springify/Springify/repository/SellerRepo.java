package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepo extends JpaRepository<Seller,Long> {

    Seller findByEmail(String email);

    List<Seller> findByAccountStatus(AccountStatus status);
}
