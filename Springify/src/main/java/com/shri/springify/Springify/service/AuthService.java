package com.shri.springify.Springify.service;

import com.shri.springify.Springify.response.SignUpRequest;

public interface AuthService {


    String createUser(SignUpRequest req) throws Exception;
}
