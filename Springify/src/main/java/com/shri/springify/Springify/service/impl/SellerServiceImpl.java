package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.config.JwtProvider;
import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.repository.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class SellerServiceImpl implements  SellerService {


    @Autowired
    private SellerRepo sellerRepo;


    @Autowired
    private JwtProvider jwtProvider;




    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);


        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) {
        return null;
    }

    @Override
    public Seller getSellerById(Long id) {
        return null;
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {

        Seller seller=sellerRepo.findByEmail(email);

        if(seller==null)
            throw  new Exception("User not found");

        return  seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus accountStatus) {
        return List.of();
    }

    @Override
    public void deleteSeller(Long id) {

    }

    @Override
    public Seller verifyEmail(String email, String otp) {
        return null;
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerid, AccountStatus accountStatus) {
        return null;
    }
}
