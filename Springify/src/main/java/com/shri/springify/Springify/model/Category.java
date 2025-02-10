////package com.shri.springify.Springify.model;
////
////import jakarta.persistence.*;
////import jakarta.validation.constraints.NotNull;
////import lombok.AllArgsConstructor;
////import lombok.Data;
////import lombok.NoArgsConstructor;
////
////@Entity
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////public class Category {
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////
////
////    private  String name;
////
////    @Column(unique = true)
////    @NotNull
////    private String categoryId;
////
////
////    @ManyToOne
////    private  Category parentCategory;
////
////   @NotNull
////    private  Integer level;
////}
//
//
//package com.shri.springify.Springify.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Category {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @Column(unique = true, nullable = false)
//    private String categoryId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_category_id")
//    private Category parentCategory;
//
//    @NotNull
//    private Integer level;
//}
//


package com.shri.springify.Springify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100) // Ensuring uniqueness and length limit
    private String name;

    @Column(unique = true, nullable = false, length = 50) // Adding length constraint
    private String categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> subCategories; // Helps retrieve child categories

    @Column(nullable = false)
    private Integer level;
}
