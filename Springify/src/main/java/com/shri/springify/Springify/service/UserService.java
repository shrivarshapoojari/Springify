package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.User;

public interface UserService {

    public User findUserByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
