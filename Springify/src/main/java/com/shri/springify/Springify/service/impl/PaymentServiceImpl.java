package com.shri.springify.Springify.service.impl;
import com.shri.springify.Springify.domain.PaymentOrderStatus;
import com.shri.springify.Springify.service.OrderService;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.shri.springify.Springify.model.Order;
import com.shri.springify.Springify.model.PaymentOrder;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.OrderRepo;
import com.shri.springify.Springify.repository.PaymentOrderRepo;
import com.shri.springify.Springify.service.PaymentService;
import com.shri.springify.Springify.service.UserService;
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
    public void proceedPaymentOrder(Event event) {
        switch (event.getType()) {
            case "checkout.session.completed":
                handlePaymentSuccess(event);
                break;
            case "payment_intent.succeeded":
                handlePaymentIntentSuccess(event);
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
            Long orderId = Long.valueOf(metadata.get("order_id"));
            Set<Order> orders=orderService.findOrderById()
            String userEmail =metadata.get("user_email");
            User user=userService.findUserByEmail(userEmail);
            String paymentLinkId=event.getId();
            PaymentOrder paymentOrder=new PaymentOrder();
            paymentOrder.setStatus(PaymentOrderStatus.SUCESS);
            paymentOrder.setPaymentLinkId(paymentLinkId);
            paymentOrder.setUser(user);
            paymentOrder.setOrders();
            System.out.println("Payment Successful for Order ID: " + orderId + ", User ID: " + userId);

        }
    }

    private void handlePaymentIntentSuccess(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        if (paymentIntent != null) {
            Map<String, String> metadata = paymentIntent.getMetadata();
            String orderId = metadata.get("order_id");
            String userId = metadata.get("user_id");

            System.out.println("Payment Intent Succeeded for Order ID: " + orderId + ", User ID: " + userId);
            // Process order fulfillment
        }
    }

    private void handlePaymentFailure(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        if (paymentIntent != null) {
            Map<String, String> metadata = paymentIntent.getMetadata();
            String orderId = metadata.get("order_id");
            String userId = metadata.get("user_id");

            System.out.println("Payment Failed for Order ID: " + orderId + ", User ID: " + userId);
            // Handle payment failure scenario
        }
    }



    @Override
    public String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {


        Stripe.apiKey = stripeSecret;

        Map<String, String> metadata = new HashMap<>();
        metadata.put("order_id", orderId.toString());
        metadata.put("user_email", user.getEmail());

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment/success")
                .setCancelUrl("http://localhost:8080/payment/cancel")
                .setCustomerEmail(user.getEmail())
                .setClientReferenceId(orderId.toString())
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
        return session.getUrl();
    }
}
