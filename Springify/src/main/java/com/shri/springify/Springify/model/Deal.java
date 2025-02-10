package com.shri.springify.Springify.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deal {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private  Integer discount;

    @OneToOne
    HomeCategory category;
}
