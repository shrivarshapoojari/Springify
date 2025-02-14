package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.model.Order;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.model.Transaction;
import com.shri.springify.Springify.repository.SellerRepo;
import com.shri.springify.Springify.repository.TransactionRepo;
import com.shri.springify.Springify.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Override
    public Transaction createTransaction(Order order) throws Exception {

        Seller seller= sellerRepo.findById(order.getSellerId()).orElseThrow(()-> new Exception("Seller not found"));

        Transaction transaction=new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);
        transaction.setDate(LocalDateTime.now());

        return  transactionRepo.save(transaction);

    }

    @Override
    public List<Transaction> getTransactionBySellerId(Long sellerId) {

        return  transactionRepo.findBySellerId(sellerId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return  transactionRepo.findAll();
    }
}
