package com.shri.springify.Springify.service.impl;
import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.domain.PaymentOrderStatus;
import com.shri.springify.Springify.domain.PaymentStatus;
import com.shri.springify.Springify.model.*;
import com.shri.springify.Springify.response.PaymentLinkResponse;
import com.shri.springify.Springify.service.*;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.shri.springify.Springify.repository.OrderRepo;
import com.shri.springify.Springify.repository.PaymentOrderRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stripe.model.Event;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepo orderRepo;


    @Autowired
    private  TransactionService transactionService;
    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerReportService sellerReportService;

    @Autowired
    private OrderService orderService;

    private  String stripeSecret="sk_test_51QsGZNIegnpeVBEiY7Ag9U8247YcXMDqi30Z0PzH1Rhlm5HwYuXAWVXFoJxLV6AsLmr6yWLAM22z6chq4hv1SYP400T6T9lLA9";

    @Override
    public PaymentOrder createOrder(String jwt, Set<Order> orders) throws Exception {
        User user=userService.findUserByJwt(jwt);
         double amount=orders.stream().mapToDouble(Order ::getTotalSellingPrice).sum();
         PaymentOrder paymentOrder=new PaymentOrder();
         paymentOrder.setAmount((long) amount);
         paymentOrder.setUser(user);
         paymentOrder.setOrders(orders);
         return  paymentOrderRepo.save(paymentOrder);

    }

    @Override
    public PaymentOrder getPaymentOrderById(Long paymentOrderId) throws Exception {

        return paymentOrderRepo.findById(paymentOrderId).orElseThrow(()->new Exception("Order not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentLinkId(String paymentLinkId) throws Exception {

        PaymentOrder paymentOrder=paymentOrderRepo.findByPaymentLinkId(paymentLinkId);

        if(paymentOrder==null)
            throw  new Exception("payment order not found");

        return  paymentOrder;
    }

    @Override
    public void proceedPaymentOrder(Event event) throws Exception {
        switch (event.getType()) {
            case "checkout.session.completed", "payment_intent.succeeded":
                handlePaymentSuccess(event);
                break;
            case "payment_intent.payment_failed":
                handlePaymentFailure(event);
                break;
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }
    }

    private void handlePaymentSuccess(Event event) throws Exception {
        Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
        if (session != null) {
            Map<String, String> metadata = session.getMetadata();
            Long paymentOrderId = Long.valueOf(metadata.get("paymentOrder_id"));
            PaymentOrder paymentOrder=paymentOrderRepo.findById(paymentOrderId).orElseThrow(()->new Exception(" Payment Order not found"));
            Set<Order> orders=paymentOrder.getOrders();
            String userEmail =metadata.get("user_email");



            paymentOrder.setStatus(PaymentOrderStatus.SUCESS);

            for(Order order:orders)
            {
                Long sellerId=order.getSellerId();
                SellerReport report=sellerReportService.getSellerReport(sellerId);
                report.setTotalOrders(report.getTotalOrders()+1);
                report.setTotalEarnings((long) (report.getTotalEarnings()+order.getTotalSellingPrice()));
                report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
                order.setOrderStatus(OrderStatus.PLACED);
                order.setPaymentStatus(PaymentStatus.COMPLETED);
                transactionService.createTransaction(order);
                orderRepo.save(order);
                sellerReportService.updateSellerReport(report);
            }




            System.out.println("Payment Successful for Order ID: " + paymentOrderId + ", User ID: " + userEmail);

        }
    }



    private void handlePaymentFailure(Event event) throws Exception {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        if (paymentIntent != null) {
            Map<String, String> metadata = paymentIntent.getMetadata();
            Long paymentOrderId = Long.valueOf(metadata.get("paymentOrder_id"));


            PaymentOrder paymentOrder=paymentOrderRepo.findById(paymentOrderId).orElseThrow(()->new Exception(" Payment Order not found"));
            Set<Order> orders=paymentOrder.getOrders();
            String userEmail =metadata.get("user_email");



            paymentOrder.setStatus(PaymentOrderStatus.FAILED);

            for(Order order:orders)
            {

                order.setOrderStatus(OrderStatus.FAILED);
                order.setPaymentStatus(PaymentStatus.FAILED);
                orderRepo.save(order);
            }




            System.out.println("Payment Failed for Order ID: " + paymentOrderId + ", User ID: " + userEmail);

        }
    }



    @Override
    public PaymentLinkResponse createStripePaymentLink(Long paymentOrderId) throws Exception {


        PaymentOrder paymentOrder=paymentOrderRepo.findById(paymentOrderId).orElseThrow(()->new Exception("Order not found"));
        User user=paymentOrder.getUser();
        Long amount=paymentOrder.getAmount();

        Stripe.apiKey = stripeSecret;

        Map<String, String> metadata = new HashMap<>();
        metadata.put("paymentOrder_id", paymentOrderId.toString());
        metadata.put("user_email", user.getEmail());

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment/success")
                .setCancelUrl("http://localhost:8080/payment/cancel")
                .setCustomerEmail(user.getEmail())
                .setClientReferenceId(paymentOrderId.toString())
                .putAllMetadata(metadata)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("inr")
                                .setUnitAmount(amount * 100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Springify")
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);
        paymentOrder.setPaymentLinkId(session.getId());
        paymentOrderRepo.save(paymentOrder);
        PaymentLinkResponse paymentLinkResponse=new PaymentLinkResponse();
        paymentLinkResponse.setPaymentLinkUrl(session.getUrl());
        paymentLinkResponse.setPaymentLinkId(session.getId());
        paymentLinkResponse.setPaymentId(paymentOrder.getId());
        return  paymentLinkResponse;
    }
}
