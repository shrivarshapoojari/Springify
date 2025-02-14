package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.model.Transaction;
import com.shri.springify.Springify.service.SellerService;
import com.shri.springify.Springify.service.TransactionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController
{


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SellerService sellerService;


    @SneakyThrows
    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getTransactionBySeller(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Seller seller=sellerService.getSellerProfile(jwt);
        Long sellerId=seller.getId();
        List<Transaction> transactions=  transactionService.getTransactionBySellerId(sellerId);
        return  new ResponseEntity<>(transactions, HttpStatus.OK);

    }
    @GetMapping("/all")
    public  ResponseEntity<List<Transaction>> getAllTransactions()
    {
        List<Transaction> transactions=  transactionService.getAllTransactions();
        return  new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
