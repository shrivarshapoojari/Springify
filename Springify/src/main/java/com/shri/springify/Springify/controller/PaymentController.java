package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.domain.PaymentOrderStatus;
import com.shri.springify.Springify.model.PaymentOrder;
import com.shri.springify.Springify.response.ApiResponse;
import com.shri.springify.Springify.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
@RestController

public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final String ENDPOINT_SECRET = "whsec_z70N8Ss1DV1zYhBQCjdtKCwQSNIyff5X";

    @PostMapping("/webhook")
    public String  handlePostPayments(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        if (sigHeader == null) {
            return "No Stripe Signature Header Found";
        }

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, ENDPOINT_SECRET);
            paymentService.proceedPaymentOrder(event);
            return "Webhook received successfully";

        } catch (SignatureVerificationException e) {
            return "Invalid signature";
        } catch (Exception e) {
            return "Webhook handling error";
        }
    }


    @GetMapping("/api/payments/{paymentId}")
    public ResponseEntity<ApiResponse> getPaymentStatus(@PathVariable Long paymentId,
                                                        @RequestHeader("Authorization") String jwt
                                                        ) throws Exception {
        PaymentOrder paymentOrder=paymentService.getPaymentOrderById(paymentId);
        ApiResponse response=new ApiResponse();
        if(paymentOrder.getStatus()== PaymentOrderStatus.SUCESS)
        {
              response.setMessage("Payment success");
        }
        else {
            response.setMessage("Payment failed");


        }
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }






}
