//package com.shri.springify.Springify.model;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//
//public class OrderItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Long id;
//
//    @JsonIgnore
//    @ManyToOne
//    private Order order;
//
//    @ManyToOne
//    private  Product product;
//
//    private String size;
//
//    private int quantity;
//
//    private Integer mrpPrice;
//
//    private Integer sellingPrice;
//
//    private  Long userId;
//
//}
package com.shri.springify.Springify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed AUTO to IDENTITY
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 20)
    private String size;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double mrpPrice; // Changed Integer to double for precision

    @Column(nullable = false)
    private double sellingPrice; // Changed Integer to double

}
