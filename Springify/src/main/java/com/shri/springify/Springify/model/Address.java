//package com.shri.springify.Springify.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//
//public class Address {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//
//    private String Locality;
//
//    private  String address;
//
//    private  String city;
//
//    private String state;
//
//    private String pinCode;
//
//    private String mobile;
//}


package com.shri.springify.Springify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String locality; // Corrected variable name

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 6) // Ensuring correct PIN code format
    private String pinCode;

    @Column(nullable = false, length = 10) // Mobile numbers should have 10 digits
    private String mobile;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Added reference to User
}

