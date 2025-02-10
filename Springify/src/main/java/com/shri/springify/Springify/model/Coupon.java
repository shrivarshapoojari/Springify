//package com.shri.springify.Springify.model;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Coupon {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private  String code;
//
//
//    private  double discountPercentage;
//
//    private LocalDate validityStartDate;
//
//    private LocalDate validityEndDate;
//
//
//    private  double minimumOrderValue;
//
//
//    private  boolean isActive=true;
//
//    @ManyToMany(mappedBy = "usedCoupons")
//    private Set<User> usedByUsers=new HashSet<>();
//
//}


package com.shri.springify.Springify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50) // Ensuring uniqueness and length limit
    private String code;

    private double discountPercentage;

    @Column(nullable = false)
    private LocalDate validityStartDate;

    @Column(nullable = false)
    private LocalDate validityEndDate;

    @Column(nullable = false)
    private double minimumOrderValue;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @ManyToMany(mappedBy = "usedCoupons")
    private Set<User> usedByUsers = new HashSet<>();
}
