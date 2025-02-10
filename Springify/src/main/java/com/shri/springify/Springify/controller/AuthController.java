package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.UserRepo;
import com.shri.springify.Springify.response.SignUpRequest;
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
    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody SignUpRequest req)

    {
        User user=new User();
         user.setEmail(req.getEmail());
         user.setFullName(req.getFullName());
         user.setPassword(req.getPassword());
User savedUser=userRepo.save(user);


        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }
}
