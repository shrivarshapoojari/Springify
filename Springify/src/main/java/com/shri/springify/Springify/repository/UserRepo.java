package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends  JpaRepository<User,Long> {

    User findByEmail(String email);
}
