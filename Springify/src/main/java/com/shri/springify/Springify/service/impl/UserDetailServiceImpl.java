package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.domain.USER_ROLE;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.SellerRepo;
import com.shri.springify.Springify.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
   @Autowired
    private UserRepo userrepo;

   @Autowired
   private SellerRepo sellerRepo;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerRepo.findByEmail(username);
        if (seller != null) {
            return buildUserDetails(seller.getEmail(), seller.getPassword(), seller.getRole());
        }

        User user = userrepo.findByEmail(username);
        if (user != null) {
            return buildUserDetails(user.getEmail(), user.getPassword(), user.getRole());
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }





    private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {

       if(role==null)
           role=USER_ROLE.ROLE_CUSTOMER;

       List<GrantedAuthority> authorityList=new ArrayList<>();
       authorityList.add(new SimpleGrantedAuthority(String.valueOf(role)));

       return new org.springframework.security.core.userdetails.User(email,password,authorityList);

    }
}
