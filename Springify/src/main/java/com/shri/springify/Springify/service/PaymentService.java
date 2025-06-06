package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.Order;
import com.shri.springify.Springify.model.PaymentOrder;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import java.util.Set;

public interface PaymentService {

    PaymentOrder createOrder(String jwt, Set<Order> orders) throws Exception;

    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;

    PaymentOrder getPaymentOrderByPaymentLinkId(String paymentOrderId) throws Exception;

    void proceedPaymentOrder(Event event) throws Exception;

   PaymentLinkResponse createStripePaymentLink(Long paymentOrderId) throws Exception;
}
