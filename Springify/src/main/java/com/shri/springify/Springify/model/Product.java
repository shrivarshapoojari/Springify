////package com.shri.springify.Springify.model;
////
////import jakarta.persistence.*;
////import lombok.AllArgsConstructor;
////import lombok.Data;
////import lombok.NoArgsConstructor;
////
////import java.time.LocalDateTime;
////import java.util.ArrayList;
////import java.util.List;
////
////@Entity
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////public class Product {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
////    private  Long id;
////
////    private  String title;
////
////    private  String description;
////
////    private  int mrpPrice;
////
////    private int sellingPrice;
////
////    private int discountPercent;
////
////    private int quantity;
////
////    private String color;
////
////
////    @ElementCollection
////    private List<String> images=new ArrayList<>();
////
////    private int numRatings;
////
////    @ManyToOne
////    private Category category;
////
////    @ManyToOne
////    private  Seller seller;
////
////
////    private LocalDateTime  createdAt;
////
////    private String size;
////
////    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
////    private List<Review> reviews=new ArrayList<>();
////
////
////}
//package com.shri.springify.Springify.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String title;
//    private String description;
//    private int mrpPrice;
//    private int sellingPrice;
//    private int discountPercent;
//    private int quantity;
//    private String color;
//    private String size;
//    private int numRatings;
//
//    @ElementCollection
//    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
//    @Column(name = "image_url")
//    private List<String> images = new ArrayList<>();
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "seller_id", nullable = false)
//    private Seller seller;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();
//}


package com.shri.springify.Springify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed AUTO to IDENTITY
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double mrpPrice; // Changed from int to double

    @Column(nullable = false)
    private double sellingPrice; // Changed from int to double

    @Column(nullable = false)
    private int discountPercent;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 50)
    private String color;

    @Column(length = 20)
    private String size;

    @Column(nullable = false)
    private int numRatings = 0;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
