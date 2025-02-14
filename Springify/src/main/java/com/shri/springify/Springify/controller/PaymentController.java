package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
@RestController
@RequestMapping("/webhook")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final String ENDPOINT_SECRET = "whsec_z70N8Ss1DV1zYhBQCjdtKCwQSNIyff5X";

    @PostMapping
    public String handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
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
}
