package com.shri.springify.Springify.model;

import com.shri.springify.Springify.domain.PaymentStatus;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PaymentDetails {


    private String stripePaymentLinkId;
    private String stripePaymentLinkReferenceId;
    private String stripePaymentLinkStatus;
    private String stripePaymentId;
    private PaymentStatus status;
}
