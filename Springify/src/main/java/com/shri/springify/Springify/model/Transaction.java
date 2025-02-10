//package com.shri.springify.Springify.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Transaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//
//    @ManyToOne
//    private  User customer;
//
//    @OneToOne
//    private  Order order;
//
//
//    @ManyToOne
//    private  Seller seller;
//
//
//
//    private LocalDateTime date=LocalDateTime.now();
//}



package com.shri.springify.Springify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed AUTO to IDENTITY
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Explicit foreign key
    private User customer;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false) // Explicit foreign key
    private Order order;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false) // Explicit foreign key
    private Seller seller;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date = LocalDateTime.now();
}
