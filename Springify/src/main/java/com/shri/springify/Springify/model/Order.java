//package com.shri.springify.Springify.model;
//
//import com.shri.springify.Springify.domain.OrderStatus;
//import com.shri.springify.Springify.domain.PaymentStatus;
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
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Long id;
//
//    private String orderId;
//
//    @ManyToOne
//    private  User user;
//
//    private  Long sellerId;
//
//    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<OrderItem> orderItems=new ArrayList<>();
//
//    @ManyToOne
//    private  Address shippingAddress;
//
//    @Embedded
//    private  PaymentDetails paymentDetails=new PaymentDetails();
//
//    private  double totalMrpPrice;
//
//    private Integer totalSellingPrice;
//
//    private Integer discount;
//
//    private OrderStatus orderStatus;
//
//    private int totalItem;
//
//    private PaymentStatus paymentStatus=PaymentStatus.PENDING;
//
//    private LocalDateTime orderDate=LocalDateTime.now();
//    private LocalDateTime deliverDate =orderDate.plusDays(7);
//
//
//
//}


package com.shri.springify.Springify.model;

import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.domain.PaymentStatus;
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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50) // Ensuring uniqueness and length limit
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    @Column(nullable = false)
    private double totalMrpPrice;

    @Column(nullable = false)
    private double totalSellingPrice;

    @Column(nullable = false)
    private double discount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING; // Default value

    @Column(nullable = false)
    private int totalItem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING; // Default value

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime deliverDate;

    @PrePersist
    protected void onCreate() {
        this.deliverDate = this.orderDate.plusDays(7); // Ensures correct initialization
    }
}
