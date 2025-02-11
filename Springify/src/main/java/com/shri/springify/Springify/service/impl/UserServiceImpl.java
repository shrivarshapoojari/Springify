package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.config.JwtProvider;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.UserRepo;
import com.shri.springify.Springify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserRepo userRepo;
    private  final JwtProvider jwtProvider;
    @Override
    public User findUserByJwt(String jwt) throws Exception {


            String email= jwtProvider.getEmailFromJwtToken(jwt);


        return this.findUserByEmail(email);



    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user= userRepo.findByEmail(email);
        if(user==null)
            throw new Exception("User not found");

        return  user;
    }
}
