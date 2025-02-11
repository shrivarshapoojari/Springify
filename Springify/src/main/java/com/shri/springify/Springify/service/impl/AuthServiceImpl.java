package com.shri.springify.Springify.service.impl;


import com.shri.springify.Springify.config.JwtProvider;
import com.shri.springify.Springify.domain.USER_ROLE;
import com.shri.springify.Springify.model.Cart;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.CartRepo;
import com.shri.springify.Springify.repository.UserRepo;
import com.shri.springify.Springify.response.SignUpRequest;
import com.shri.springify.Springify.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class AuthServiceImpl implements AuthService {
   @Autowired
    private  final UserRepo userRepo;

   @Autowired
   private  final PasswordEncoder passwordEncoder;

   @Autowired
   private  final CartRepo cartRepo;

   @Autowired
   private  final JwtProvider jwtProvider;
    @Override
    public String createUser(SignUpRequest req) throws Exception {
          User user=userRepo.findByEmail(req.getEmail());


          if(user==null)
          {
               User createdUser=new User();
               createdUser.setEmail(req.getEmail());
               createdUser.setFullName(req.getFullName());
               createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
               createdUser.setMobile("1234567890");
               createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

               user=userRepo.save(createdUser);


               Cart cart =new Cart();
               cart.setUser(user);
               cartRepo.save(cart);

               List<GrantedAuthority> authorities=new ArrayList<>();

               authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

              Authentication authentication=new UsernamePasswordAuthenticationToken(req.getEmail(),null,authorities);
              SecurityContextHolder.getContext().setAuthentication(authentication);

              String token= jwtProvider.generateToken(authentication);
              System.out.println("TOken  is" +token);
              return  token;
          }
          else {
              throw  new Exception("User already exists");
          }
    }
}
