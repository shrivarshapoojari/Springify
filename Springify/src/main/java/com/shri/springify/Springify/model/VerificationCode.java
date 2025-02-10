package com.shri.springify.Springify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;


    private  String otp;

    private String email;

    @OneToOne
    private  User user;

    @OneToOne
    private  Seller seller;
}
