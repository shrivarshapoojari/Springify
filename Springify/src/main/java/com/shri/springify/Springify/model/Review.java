//////package com.shri.springify.Springify.model;
//////
//////import com.fasterxml.jackson.annotation.JsonIgnore;
//////import jakarta.persistence.*;
//////import lombok.AllArgsConstructor;
//////import lombok.Data;
//////import lombok.NoArgsConstructor;
//////
//////import java.time.LocalDateTime;
//////import java.util.List;
//////
//////@Entity
//////@Data
//////@AllArgsConstructor
//////@NoArgsConstructor
//////public class Review {
//////
//////    @Id
//////    @GeneratedValue(strategy = GenerationType.AUTO)
//////    private Long id;
//////
//////
//////    @Column(nullable = false)
//////    private  String reviewText;
//////
//////    @Column(nullable = false)
//////    private double rating;
//////
//////    @ElementCollection
//////    private List<String> prouctImages;
//////
//////
//////
//////    @JsonIgnore
//////    @ManyToOne
//////    @JoinColumn(name="product_id",nullable = false)
//////    private  Product product;
//////
//////    @ManyToOne
//////    @JoinColumn(name="user_id",nullable = false)
//////    private  User user;
//////
//////    @Column(nullable = false)
//////    private LocalDateTime createdAt=LocalDateTime.now();
//////}
////
////
////package com.shri.springify.Springify.model;
////
////import com.fasterxml.jackson.annotation.JsonIgnore;
////import jakarta.persistence.*;
////import lombok.AllArgsConstructor;
////import lombok.Data;
////import lombok.NoArgsConstructor;
////
////import java.time.LocalDateTime;
////import java.util.List;
////
////@Entity
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////public class Review {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for better compatibility
////    private Long id;
////
////    @Column(nullable = false)
////    private String reviewText;
////
////    @Column(nullable = false)
////    private double rating;
////
////    // Corrected field name and added proper collection mapping
////    @ElementCollection
////    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "review_id"))
////    @Column(name = "image_url")
////    private List<String> productImages;
////
////    @JsonIgnore
////    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for better performance
////    @JoinColumn(name = "product_id", nullable = false)
////    private Product product;
////
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "user_id", nullable = false)
////    private User user;
////
////    @Column(nullable = false, updatable = false)
////    private LocalDateTime createdAt = LocalDateTime.now();
////}
////
//
//
//
//package com.shri.springify.Springify.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Review {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String reviewText;
//
//    @Column(nullable = false)
//    private double rating;
//
//    @ElementCollection
//    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "review_id"))
//    @Column(name = "image_url")
//    private List<String> productImages;
//
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//}



package com.shri.springify.Springify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed AUTO to IDENTITY
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reviewText;

    @Column(nullable = false)
    private double rating;

    @ElementCollection
    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url")
    private List<String> productImages;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for performance
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for performance
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
