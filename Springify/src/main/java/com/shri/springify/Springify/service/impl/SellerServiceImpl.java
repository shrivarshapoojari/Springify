package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.config.JwtProvider;
import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.domain.USER_ROLE;
import com.shri.springify.Springify.model.Address;
import com.shri.springify.Springify.model.BankDetails;
import com.shri.springify.Springify.model.BusinessDetails;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.repository.AddressRepo;
import com.shri.springify.Springify.repository.SellerRepo;
import com.shri.springify.Springify.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class SellerServiceImpl implements SellerService {


    @Autowired
    private SellerRepo sellerRepo;


    @Autowired
    private JwtProvider jwtProvider;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);


        return this.getSellerByEmail(email);
    }

//    @Override
//    public Seller createSeller(Seller seller) throws Exception {
//          Seller sellerExist=sellerRepo.findByEmail(seller.getEmail());
//          if(sellerExist!=null)
//              throw  new Exception("Seller already exist");
//
//          Address savedAddress=addressRepo.save(seller.getPickupAddress());
//          Seller newSeller= new Seller();
//          newSeller.setSellerName(seller.getSellerName());
//          newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
//          newSeller.setEmail(seller.getEmail());
//
//          newSeller.setPickupAddress(savedAddress);
//          newSeller.setRole(USER_ROLE.ROLE_SELLER);
//          newSeller.setMobile(seller.getMobile());
//          newSeller.setBankDetails(seller.getBankDetails());
//          newSeller.setBusinessDetails(seller.getBusinessDetails());
//          newSeller.setPickupAddress(savedAddress);
//       Seller savedSeller=  sellerRepo.save(newSeller);
//         savedAddress.setSeller(savedSeller);
//         return savedSeller;
//    }










@Override
public Seller createSeller(Seller seller) throws Exception {
    Seller sellerExist = sellerRepo.findByEmail(seller.getEmail());
    if (sellerExist != null) {
        throw new Exception("Seller already exists");
    }

    Address pickupAddress = new Address();
    pickupAddress.setName(seller.getPickupAddress().getName());
    pickupAddress.setLocality(seller.getPickupAddress().getLocality());
    pickupAddress.setAddress(seller.getPickupAddress().getAddress());
    pickupAddress.setCity(seller.getPickupAddress().getCity());
    pickupAddress.setState(seller.getPickupAddress().getState());
    pickupAddress.setPinCode(seller.getPickupAddress().getPinCode());
    pickupAddress.setMobile(seller.getPickupAddress().getMobile());

    Address savedAddress = addressRepo.save(pickupAddress);

    Seller newSeller = new Seller();
    newSeller.setSellerName(seller.getSellerName());
    newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
    newSeller.setEmail(seller.getEmail());
    newSeller.setRole(USER_ROLE.ROLE_SELLER);
    newSeller.setMobile(seller.getMobile());

    BankDetails bankDetails = new BankDetails();
    if (seller.getBankDetails() != null) {
        System.out.println(seller.getBankDetails());
        bankDetails.setAccountNumber(seller.getBankDetails().getAccountNumber());
        bankDetails.setAccountHolderName(seller.getBankDetails().getAccountHolderName());
        bankDetails.setIfscCode(seller.getBankDetails().getIfscCode());
    }
    newSeller.setBankDetails(bankDetails);

    BusinessDetails businessDetails = new BusinessDetails();
    if (seller.getBusinessDetails() != null) {
        System.out.println(seller.getBusinessDetails());
        businessDetails.setBusinessName(seller.getBusinessDetails().getBusinessName());
        businessDetails.setBusinessMobile(seller.getBusinessDetails().getBusinessMobile());
        businessDetails.setBusinessEmail(seller.getBusinessDetails().getBusinessEmail());
        businessDetails.setBusinessAddress(seller.getBusinessDetails().getBusinessAddress());
        businessDetails.setLogo(seller.getBusinessDetails().getLogo());
        businessDetails.setBanner(seller.getBusinessDetails().getBanner());
    } else {
        businessDetails.setBusinessName("");
        businessDetails.setBusinessMobile("");
        businessDetails.setBusinessEmail("");
        businessDetails.setBusinessAddress("");
        businessDetails.setLogo("");
        businessDetails.setBanner("");
    }
    newSeller.setBusinessDetails(businessDetails);

    newSeller.setPickupAddress(savedAddress);

    Seller savedSeller = sellerRepo.save(newSeller);

    savedAddress.setSeller(savedSeller);

    return savedSeller;
}













    @Override
    public Seller getSellerById(Long id) throws Exception {

        return sellerRepo.findById(id).orElseThrow(()->new Exception("Seller not found"));
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

        return sellerRepo.findByAccountStatus(accountStatus);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
              Seller seller=this.getSellerById(id);

              sellerRepo.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {

        Seller seller=getSellerByEmail(email);
        seller.setIsEmailVerified(true);
        return  sellerRepo.save(seller);

    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerid, AccountStatus accountStatus) throws Exception {
        Seller seller=this.getSellerById(sellerid);
        seller.setAccountStatus(accountStatus);
        return  sellerRepo.save(seller);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
//         Seller existingSeller=sellerRepo.findById(id).orElseThrow(()-> new Exception("Seller Not found"));
//
//         if(seller.getSellerName()!=null)
//             existingSeller.setSellerName(seller.getSellerName());
//
//         if(seller.getMobile()!=null)
//             existingSeller.setMobile(seller.getMobile());
//
//         if(seller.getEmail()!=null)
//             existingSeller.setEmail(seller.getEmail());
//
//
//        if (seller.getBankDetails() != null) {
//
//
//
//            BankDetails existingBankDetails = existingSeller.getBankDetails();
//            if (seller.getBankDetails().getAccountNumber() != null)
//                existingBankDetails.setAccountNumber(seller.getBankDetails().getAccountNumber());
//            if (seller.getBankDetails().getAccountHolderName() != null)
//                existingBankDetails.setAccountHolderName(seller.getBankDetails().getAccountHolderName());
//            if (seller.getBankDetails().getIfscCode() != null)
//                existingBankDetails.setIfscCode(seller.getBankDetails().getIfscCode());
//        }
//
//        if (seller.getBusinessDetails() != null) {
//
//                existingSeller.setBusinessDetails(new BusinessDetails());
//
//            BusinessDetails existingBusinessDetails = existingSeller.getBusinessDetails();
//            if (seller.getBusinessDetails().getBusinessName() != null)
//                existingBusinessDetails.setBusinessName(seller.getBusinessDetails().getBusinessName());
//            if (seller.getBusinessDetails().getBusinessMobile() != null)
//                existingBusinessDetails.setBusinessMobile(seller.getBusinessDetails().getBusinessMobile());
//            if (seller.getBusinessDetails().getBusinessEmail() != null)
//                existingBusinessDetails.setBusinessEmail(seller.getBusinessDetails().getBusinessEmail());
//            if (seller.getBusinessDetails().getBusinessAddress() != null)
//                existingBusinessDetails.setBusinessAddress(seller.getBusinessDetails().getBusinessAddress());
//            if (seller.getBusinessDetails().getLogo() != null)
//                existingBusinessDetails.setLogo(seller.getBusinessDetails().getLogo());
//            if (seller.getBusinessDetails().getBanner() != null)
//                existingBusinessDetails.setBanner(seller.getBusinessDetails().getBanner());
//        }
//
//
//            if(seller.getPickupAddress()!=null)
//             existingSeller.setPickupAddress(seller.getPickupAddress());
//
//
//
//
//
//         return  sellerRepo.save(existingSeller);



        Seller existingSeller = sellerRepo.findById(id)
                .orElseThrow(() -> new Exception("Seller not found"));

        if (seller.getSellerName() != null)
            existingSeller.setSellerName(seller.getSellerName());

        if (seller.getEmail() != null)
            existingSeller.setEmail(seller.getEmail());

        if (seller.getPassword() != null)
            existingSeller.setPassword(passwordEncoder.encode(seller.getPassword()));

        if (seller.getMobile() != null)
            existingSeller.setMobile(seller.getMobile());

        if (seller.getRole() != null)
            existingSeller.setRole(seller.getRole());


        if (seller.getBankDetails() != null) {
            if (existingSeller.getBankDetails() == null)
                existingSeller.setBankDetails(new BankDetails());

            BankDetails existingBankDetails = existingSeller.getBankDetails();
            if (seller.getBankDetails().getAccountNumber() != null)
                existingBankDetails.setAccountNumber(seller.getBankDetails().getAccountNumber());
            if (seller.getBankDetails().getAccountHolderName() != null)
                existingBankDetails.setAccountHolderName(seller.getBankDetails().getAccountHolderName());
            if (seller.getBankDetails().getIfscCode() != null)
                existingBankDetails.setIfscCode(seller.getBankDetails().getIfscCode());
        }


        if (seller.getBusinessDetails() != null) {
            if (existingSeller.getBusinessDetails() == null)
                existingSeller.setBusinessDetails(new BusinessDetails());

            BusinessDetails existingBusinessDetails = existingSeller.getBusinessDetails();
            if (seller.getBusinessDetails().getBusinessName() != null)
                existingBusinessDetails.setBusinessName(seller.getBusinessDetails().getBusinessName());
            if (seller.getBusinessDetails().getBusinessMobile() != null)
                existingBusinessDetails.setBusinessMobile(seller.getBusinessDetails().getBusinessMobile());
            if (seller.getBusinessDetails().getBusinessEmail() != null)
                existingBusinessDetails.setBusinessEmail(seller.getBusinessDetails().getBusinessEmail());
            if (seller.getBusinessDetails().getBusinessAddress() != null)
                existingBusinessDetails.setBusinessAddress(seller.getBusinessDetails().getBusinessAddress());
            if (seller.getBusinessDetails().getLogo() != null)
                existingBusinessDetails.setLogo(seller.getBusinessDetails().getLogo());
            if (seller.getBusinessDetails().getBanner() != null)
                existingBusinessDetails.setBanner(seller.getBusinessDetails().getBanner());
        }


        if (seller.getPickupAddress() != null) {
            if (existingSeller.getPickupAddress() == null)
                existingSeller.setPickupAddress(new Address());

            Address existingAddress = existingSeller.getPickupAddress();
            if (seller.getPickupAddress().getName() != null)
                existingAddress.setName(seller.getPickupAddress().getName());
            if (seller.getPickupAddress().getLocality() != null)
                existingAddress.setLocality(seller.getPickupAddress().getLocality());
            if (seller.getPickupAddress().getAddress() != null)
                existingAddress.setAddress(seller.getPickupAddress().getAddress());
            if (seller.getPickupAddress().getCity() != null)
                existingAddress.setCity(seller.getPickupAddress().getCity());
            if (seller.getPickupAddress().getState() != null)
                existingAddress.setState(seller.getPickupAddress().getState());
            if (seller.getPickupAddress().getPinCode() != null)
                existingAddress.setPinCode(seller.getPickupAddress().getPinCode());
            if (seller.getPickupAddress().getMobile() != null)
                existingAddress.setMobile(seller.getPickupAddress().getMobile());

            addressRepo.save(existingAddress);
        }

        return sellerRepo.save(existingSeller);
    }
}
