package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.domain.USER_ROLE;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.model.VerificationCode;
import com.shri.springify.Springify.repository.UserRepo;
import com.shri.springify.Springify.response.*;
import com.shri.springify.Springify.service.AuthService;
import com.shri.springify.Springify.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody SignUpRequest req) throws Exception {
        AuthResponse res=new AuthResponse();
        try{
            String jwt=authService.createUser(req);

            res.setJwt(jwt);
            res.setMessage("signup success");
            res.setRole(USER_ROLE.ROLE_CUSTOMER);


            return  new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e)
        {
            res.setRole(null);
            res.setJwt(null);
            res.setMessage(e.getMessage());
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) throws Exception {
        AuthResponse res=new AuthResponse();
        try{
            res=authService.login(req);


            return  new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e)
        {
            res.setRole(null);
            res.setJwt(null);
            res.setMessage(e.getMessage());
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/sendSignUpOtp")
    public  ResponseEntity<ApiResponse> sendOtp(@RequestBody VerificationCode req) throws Exception
    {     ApiResponse res=new ApiResponse();
        try {
            authService.sendSignupOtp(req.getEmail());
            res.setMessage("Otp sent");
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e)
        {
            System.out.println(e);
            res.setMessage("Failed to send otp"+e.getMessage());
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/sendLoginOtp")
    public  ResponseEntity<ApiResponse> sendLoginOtp(@RequestBody LoginOtpRequest req) throws Exception
    {     ApiResponse res=new ApiResponse();
        try {
            authService.sendLoginOtp(req.getEmail(),req.getRole());
            res.setMessage("Otp sent");
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e)
        {
            System.out.println(e);
            res.setMessage("Failed to send otp"+e.getMessage());
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }






}
