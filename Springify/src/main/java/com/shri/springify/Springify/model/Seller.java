////package com.shri.springify.Springify.model;
////
////import com.shri.springify.Springify.domain.AccountStatus;
////import com.shri.springify.Springify.domain.USER_ROLE;
////import jakarta.persistence.*;
////import lombok.AllArgsConstructor;
////import lombok.Data;
////import lombok.NoArgsConstructor;
////
////@Entity
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////public class Seller {
////
////    @Id
////    @GeneratedValue(strategy=GenerationType.AUTO)
////    Long id;
////
////
////    private String sellerName;
////
////    private String mobile;
////
////    @Column(unique = true,nullable = false)
////    private  String email;
////
////    private  String password;
////
////    @Embedded
////    private BankDetails bankDetails=new BankDetails();
////
////    @Embedded
////    private BussinessDetails bussinessDetails=new BussinessDetails();
////
////    private USER_ROLE role=USER_ROLE.ROLE_SELLER;
////
////    private  Boolean isEmailVerified=false;
////
////    private AccountStatus accountStatus=AccountStatus.PENDING_VERIFICATION;
////
////
////}
//
//
//
//package com.shri.springify.Springify.model;
//
//import com.shri.springify.Springify.domain.AccountStatus;
//import com.shri.springify.Springify.domain.USER_ROLE;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Seller {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String sellerName;
//    private String mobile;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    private String password;
//
//    @Embedded
//    private BankDetails bankDetails = new BankDetails();
//
//    @Embedded
//    private BusinessDetails businessDetails = new BusinessDetails();
//
//    @Enumerated(EnumType.STRING)
//    private USER_ROLE role = USER_ROLE.ROLE_SELLER;
//
//    private Boolean isEmailVerified = false;
//
//    @Enumerated(EnumType.STRING)
//    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
//}
//
package com.shri.springify.Springify.model;

import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed AUTO to IDENTITY
    private Long id;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_SELLER;

    @Column(nullable = false)
    private Boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;




    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address pickupAddress;

}
