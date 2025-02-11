package com.shri.springify.Springify.service;

import com.shri.springify.Springify.response.AuthResponse;
import com.shri.springify.Springify.response.LoginRequest;
import com.shri.springify.Springify.response.SignUpRequest;

public interface AuthService {

   void sendLoginOtp(String email) throws Exception;

    void sendSignupOtp(String email) throws Exception;

    String createUser(SignUpRequest req) throws Exception;

    AuthResponse login(LoginRequest req) throws  Exception;
}
