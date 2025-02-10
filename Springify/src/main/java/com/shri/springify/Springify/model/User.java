package com.shri.springify.Springify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shri.springify.Springify.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;

    private  String email;

    private  String fullName;

    private  String mobile;

    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

    @OneToMany
    private Set<Address> addresses=new HashSet<>();

    @ManyToMany
    @JsonIgnore
    private Set<Coupon> usedCoupons=new HashSet<>();
}
