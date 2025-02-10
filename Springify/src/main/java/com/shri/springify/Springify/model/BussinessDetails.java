package com.shri.springify.Springify.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BussinessDetails {

    private String businessName;
    private String businessMobile;

    private String businessEmail;

    private  String businessAddress;

    private String logo;
    private String banner;
}
