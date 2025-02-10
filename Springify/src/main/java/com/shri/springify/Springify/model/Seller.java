package com.shri.springify.Springify.model;

import com.shri.springify.Springify.domain.AccountStatus;
import com.shri.springify.Springify.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;


    private String sellerName;

    private String mobile;

    @Column(unique = true,nullable = false)
    private  String email;

    private  String password;

    @Embedded
    private BankDetails bankDetails=new BankDetails();

    @Embedded
    private BussinessDetails bussinessDetails=new BussinessDetails();

    private USER_ROLE role=USER_ROLE.ROLE_SELLER;

    private  Boolean isEmailVerified=false;

    private AccountStatus accountStatus=AccountStatus.PENDING_VERIFICATION;


}
