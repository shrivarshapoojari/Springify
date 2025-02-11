package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.model.Deal;
import com.shri.springify.Springify.model.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller);
    Seller getSellerById(Long id);
    Seller getSellerByEmail(String Email) throws Exception;

    List<Seller>getAllSellers(AccountStatus accountStatus);

    void deleteSeller(Long id);

    Seller verifyEmail(String email,String otp);

    Seller updateSellerAccountStatus(Long sellerid,AccountStatus accountStatus);
}
