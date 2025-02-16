package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long> {

    PaymentOrder findByPaymentLinkId(String paymentLinkId);
    @Query("SELECT p FROM PaymentOrder p JOIN FETCH p.orders WHERE p.id = :id")
    PaymentOrder findByIdWithOrders(@Param("id") Long id);
}
