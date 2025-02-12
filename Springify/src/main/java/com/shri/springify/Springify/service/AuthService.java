package com.shri.springify.Springify.service;

import com.shri.springify.Springify.domain.USER_ROLE;
import com.shri.springify.Springify.response.AuthResponse;
import com.shri.springify.Springify.response.LoginRequest;
import com.shri.springify.Springify.response.SignUpRequest;

public interface AuthService {

   void sendLoginOtp(String email, USER_ROLE role) throws Exception;

    void sendSignupOtp(String email) throws Exception;

    String createUser(SignUpRequest req) throws Exception;

    AuthResponse login(LoginRequest req) throws  Exception;
}
