package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.Utils.OtpUtil;
import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.model.SellerReport;
import com.shri.springify.Springify.model.VerificationCode;
import com.shri.springify.Springify.repository.VerificationCodeRepo;
import com.shri.springify.Springify.response.ApiResponse;
import com.shri.springify.Springify.response.AuthResponse;
import com.shri.springify.Springify.response.LoginRequest;
import com.shri.springify.Springify.service.AuthService;
import com.shri.springify.Springify.service.EmailService;
import com.shri.springify.Springify.service.SellerReportService;
import com.shri.springify.Springify.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private VerificationCodeRepo verificationCodeRepo;

    @Autowired
    private AuthService authService;


    @Autowired
    private SellerReportService sellerReportService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) throws Exception {





        AuthResponse authResponse=authService.login(req);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);



    }


    @PatchMapping("/verify")
    public  ResponseEntity<Seller>verifySellerEmail(@RequestBody VerificationCode req) throws Exception {
        VerificationCode verificationCode=verificationCodeRepo.findByEmail(req.getEmail());
        if(verificationCode==null)
            throw new Exception("No Email found");

        if(!verificationCode.getOtp().equals(req.getOtp()))
            throw new Exception("Invalid Otp");


        Seller seller=sellerService.verifyEmail(verificationCode.getEmail(),verificationCode.getOtp());
        return  new ResponseEntity<>(seller,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
        Seller savedSeller=sellerService.createSeller(seller);
        String otp= OtpUtil.generateOtp();

        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepo.save(verificationCode);

        String subject="Verify your seller account in Springify";
        String text="Welcome to springify .. Please use the given otp to verify your account wiht Springify " +verificationCode.getOtp();


        emailService.sendVerificationEmail(verificationCode.getEmail(),subject,otp,text);

        return  new ResponseEntity<>(savedSeller,HttpStatus.OK);

    }



    @GetMapping("/{id}")
    public  ResponseEntity<Seller> getSellerById(@PathVariable  Long id) throws Exception {
        Seller seller=sellerService.getSellerById(id);
        return  new ResponseEntity<>(seller,HttpStatus.OK);
    }


    @GetMapping("/profile")
    public  ResponseEntity<Seller> getSellerByJwt(
            @RequestHeader("Authorization") String jwt
    ) throws  Exception
    {
        Seller seller=sellerService.getSellerProfile(jwt);
        return  new ResponseEntity<>(seller,HttpStatus.OK);
    }


    @GetMapping("/getAllSellers"
    )
    public  ResponseEntity<List<Seller>> getAllSeller(@RequestParam AccountStatus accountStatus)

    {
        List<Seller> sellers=sellerService.getAllSellers(accountStatus);
        return  new ResponseEntity<>(sellers,HttpStatus.OK);

    }



    @PatchMapping("/update")
   public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt,
    @RequestBody Seller seller
    ) throws Exception {
        Seller sellerProfile=sellerService.getSellerProfile(jwt);
        Seller updatedSeller=sellerService.updateSeller(sellerProfile.getId(),seller);
        return  new ResponseEntity<>(updatedSeller,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<ApiResponse> deleteSeller(@PathVariable Long id) throws Exception {
        sellerService.deleteSeller(id);
        ApiResponse response=new ApiResponse();
        response.setMessage("Deleted account successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }







    @GetMapping("/report")
    public  ResponseEntity<SellerReport> getSellerReport(
            @RequestHeader("Authorization") String jwt
    ) throws  Exception
    {
        Seller seller=sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(sellerReportService.getSellerReport(seller.getId()),HttpStatus.OK);
    }







}
