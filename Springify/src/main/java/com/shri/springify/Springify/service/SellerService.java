package com.shri.springify.Springify.service;

import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.model.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws Exception;
    Seller getSellerByEmail(String Email) throws Exception;

    List<Seller>getAllSellers(AccountStatus accountStatus);

    void deleteSeller(Long id) throws Exception;

    Seller verifyEmail(String email,String otp) throws Exception;

    Seller updateSellerAccountStatus(Long sellerid,AccountStatus accountStatus) throws Exception;

    Seller updateSeller(Long id, Seller seller) throws Exception;
}
