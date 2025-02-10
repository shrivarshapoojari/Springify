package com.shri.springify.Springify.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BusinessDetails {

    private String businessName;
    private String businessMobile;

    private String businessEmail;

    private  String businessAddress;

    private String logo;
    private String banner;
}
