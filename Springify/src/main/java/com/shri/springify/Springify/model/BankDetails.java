package com.shri.springify.Springify.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BankDetails {

    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;

}
