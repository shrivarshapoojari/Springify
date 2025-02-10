package com.shri.springify.Springify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private  String name;

    @Column(unique = true)
    @NotNull
    private String categoryId;


    @ManyToOne
    private  Category parentCategory;

   @NotNull
    private  Integer level;
}
