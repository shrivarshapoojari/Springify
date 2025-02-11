package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode,Long> {

    VerificationCode findByEmail(String email);
}
