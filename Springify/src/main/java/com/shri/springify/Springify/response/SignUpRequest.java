package com.shri.springify.Springify.response;


import com.shri.springify.Springify.domain.USER_ROLE;
import lombok.Data;

@Data
public class SignUpRequest {
    private  String email;
    private String fullName;
     private  String password;
    private String otp;
    private USER_ROLE role;
}
