package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo  extends JpaRepository<Transaction,Long> {

    List<Transaction> findBySellerId(Long sellerId);
}
