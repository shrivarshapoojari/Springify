//package com.shri.springify.Springify.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class CartItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Long id;
//
//    @ManyToOne
//    @JsonIgnore
//    private  Cart cart;
//
//    private Product product;
//
//    private String size;
//
//    private int quantity=1;
//
//    private  Integer mrpPrice;
//
//    private  Integer SellingPrice;
//
//
//    private Long userId;
//
//}


package com.shri.springify.Springify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String size;

    private int quantity = 1;

    private Integer mrpPrice;

    private Integer sellingPrice;
}

