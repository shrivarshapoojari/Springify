package com.shri.springify.Springify.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shri.springify.Springify.domain.PaymentMethod;
import com.shri.springify.Springify.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Long amount;

    private PaymentOrderStatus status= PaymentOrderStatus.PENDING;

    private String paymentLinkId;

    @ManyToOne
    private  User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "paymentOrder", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<Order> orders=new HashSet<>();



}


