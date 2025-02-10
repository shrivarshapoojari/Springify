package com.shri.springify.Springify.response;


import lombok.Data;

@Data
public class SignUpRequest {
    private  String email;
    private String fullName;
     private  String password;
    private String otp;
}
