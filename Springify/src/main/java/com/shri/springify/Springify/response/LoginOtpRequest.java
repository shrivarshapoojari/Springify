package com.shri.springify.Springify.response;

import com.shri.springify.Springify.domain.USER_ROLE;
import lombok.Data;

@Data
public class LoginOtpRequest {
   private String email;
    private USER_ROLE role;
}
