package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private SellerService sellerService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerAccountStatus(
            @PathVariable Long id,
            @PathVariable AccountStatus accountStatus
            ) throws Exception {
        Seller updatedSeller=sellerService.updateSellerAccountStatus(id, accountStatus);
        return new ResponseEntity<>(updatedSeller,HttpStatus.OK );
    }
}
