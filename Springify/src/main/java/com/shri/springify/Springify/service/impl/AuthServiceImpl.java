package com.shri.springify.Springify.service.impl;


import com.shri.springify.Springify.Utils.OtpUtil;
import com.shri.springify.Springify.config.JwtProvider;
import com.shri.springify.Springify.domain.USER_ROLE;
import com.shri.springify.Springify.model.Cart;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.model.VerificationCode;
import com.shri.springify.Springify.repository.CartRepo;
import com.shri.springify.Springify.repository.UserRepo;
import com.shri.springify.Springify.repository.VerificationCodeRepo;
import com.shri.springify.Springify.response.AuthResponse;
import com.shri.springify.Springify.response.LoginRequest;
import com.shri.springify.Springify.response.SignUpRequest;
import com.shri.springify.Springify.service.AuthService;
import com.shri.springify.Springify.service.EmailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

   @Autowired
   private  final VerificationCodeRepo verificationCodeRepo;

   @Autowired
   private  final EmailService emailService;

   @Autowired
   private final UserDetailServiceImpl userService;
    @Override
    public void sendLoginOtp(String email) throws Exception {

        User user=userRepo.findByEmail(email);
        if(user==null)
            throw new Exception("User not found");

        VerificationCode isExist=verificationCodeRepo.findByEmail(email);
        if(isExist!=null)
            verificationCodeRepo.delete(isExist);

        String otp= OtpUtil.generateOtp();
        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setOtp(otp);
        verificationCodeRepo.save(verificationCode);

        String sub="Otp for Logging into Springify";
        String text="your otp to login is " + otp;
        emailService.sendVerificationEmail(email,otp,sub,text);

    }
    @Override
    public void sendSignupOtp(String email) throws Exception {



        VerificationCode isExist=verificationCodeRepo.findByEmail(email);
        if(isExist!=null)
            verificationCodeRepo.delete(isExist);

        String otp= OtpUtil.generateOtp();
        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setOtp(otp);
        verificationCodeRepo.save(verificationCode);

        String sub="Otp for creating account in Springify";
        String text="your otp to creating account in springify is " + otp;
        emailService.sendVerificationEmail(email,otp,sub,text);

    }

    @Override
    public String createUser(SignUpRequest req) throws Exception {

        VerificationCode verificationCode=verificationCodeRepo.findByEmail(req.getEmail());

         if(verificationCode==null  || !verificationCode.getOtp().equals(req.getOtp()))
         {
             throw  new Exception("INVALID OTP");
         }




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

              return jwtProvider.generateToken(authentication);
          }
          else {
              throw  new Exception("User already exists");
          }
    }

    @Override
    public AuthResponse login(LoginRequest req) throws Exception {
         String username=req.getEmail();
         String otp=req.getOtp();

         Authentication authentication=authenticate(username,otp);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String token=jwtProvider.generateToken(authentication);
         AuthResponse authResponse=new AuthResponse();
         authResponse.setJwt(token);
        Collection<? extends  GrantedAuthority> authorities=authentication.getAuthorities();
        String roleName=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        authResponse.setRole(USER_ROLE.valueOf(roleName));
         authResponse.setMessage("Login Success");
         return  authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {

        UserDetails userDetails=userService.loadUserByUsername(username);
        if(userDetails==null)
            throw new BadCredentialsException("Invalid username ");

        VerificationCode verificationCode=verificationCodeRepo.findByEmail(username);

        if(verificationCode==null || !verificationCode.getOtp().equals(otp))
            throw  new Exception("Invalid otp");


        verificationCodeRepo.delete(verificationCode);

         return  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
