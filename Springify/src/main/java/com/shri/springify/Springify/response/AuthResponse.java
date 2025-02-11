package com.shri.springify.Springify.response;


import com.shri.springify.Springify.domain.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    public String jwt;
    private  String message;
    private USER_ROLE role;
}
