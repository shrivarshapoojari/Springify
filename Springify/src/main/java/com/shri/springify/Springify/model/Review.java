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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private  String reviewText;

    @Column(nullable = false)
    private double rating;

    @ElementCollection
    private List<String> prouctImages;



    @JsonIgnore
    @ManyToOne
    @Column  (nullable = false)    private  Product product;

    @ManyToOne
    @Column  (nullable = false)
    private  User user;

    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();
}
