package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.Order;
import com.shri.springify.Springify.model.Transaction;

import java.util.List;

public interface TransactionService {


    Transaction createTransaction(Order order) throws Exception;
    List<Transaction> getTransactionBySellerId(Long sellerId);
    List<Transaction> getAllTransactions();
}
