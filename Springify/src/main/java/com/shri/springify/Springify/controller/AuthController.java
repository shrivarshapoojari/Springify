package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.domain.USER_ROLE;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.UserRepo;
import com.shri.springify.Springify.response.AuthResponse;
import com.shri.springify.Springify.response.SignUpRequest;
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





}
