package com.shri.springify.Springify.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {

    private  String paymentLinkUrl;
    private String paymentLinkId;
    private Long paymentId;
}
