package com.shri.springify.Springify.model;


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
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    private User user;


    @OneToMany(mappedBy = "cart", cascade =CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems=new HashSet<>();

    private  double totalSellingPrice;

    private int totalItem;

    private int TotalMrpPrice;

    private int discount;

    private String couponcode;
}
