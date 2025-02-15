package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.domain.PaymentOrderStatus;
import com.shri.springify.Springify.domain.PaymentStatus;
import com.shri.springify.Springify.model.*;
import com.shri.springify.Springify.repository.AddressRepo;
import com.shri.springify.Springify.repository.OrderItemRepo;
import com.shri.springify.Springify.repository.OrderRepo;
import com.shri.springify.Springify.repository.PaymentOrderRepo;
import com.shri.springify.Springify.service.CartService;
import com.shri.springify.Springify.service.OrderService;
import com.shri.springify.Springify.service.SellerService;
import com.shri.springify.Springify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    UserService userService;

    @Autowired
    SellerService sellerService;

@Autowired
    CartService cartService;
    @Autowired
    AddressRepo addressRepo;

    @Autowired
    OrderItemRepo orderItemRepo;

    @Autowired
    PaymentOrderRepo paymentOrderRepo;

    @Override
    public Set<Order> createOrder(String jwt, Address shippingAddress) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Cart cart=cartService.findUsersCart(jwt);
        Long totalOrderValue=0L;
        if(!user.getAddresses().contains(shippingAddress))
        {
            user.getAddresses().add(shippingAddress);
        }
        Address address=addressRepo.save(shippingAddress);

        Map<Long,List<CartItem>> itemsBySeller=cart.getCartItems().stream().collect(Collectors.groupingBy(item->item.getProduct().getSeller().getId()));
     Set<Order> orders=new HashSet<>();
        for (Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {
            Long sellerId = entry.getKey();
            List<CartItem> items = entry.getValue();

            int totalOrderPrice = items.stream()
                    .mapToInt(CartItem::getSellingPrice)
                    .sum();
            int totalItem = items.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            Order createdOrder = new Order();

            createdOrder.setOrderId(UUID.randomUUID().toString());
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setShippingAddress(address);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.setPaymentStatus(PaymentStatus.PENDING);
            totalOrderValue+=totalOrderPrice;
            Order savedOrder=orderRepo.save(createdOrder);
            orders.add(savedOrder);

            List<OrderItem> orderItems=new ArrayList<>();

            for(CartItem item:items)
            {
                OrderItem orderItem=new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setProduct(item.getProduct());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setUserId(createdOrder.getUser().getId());
                orderItem.setSellingPrice(item.getSellingPrice());

                savedOrder.getOrderItems().add(orderItem);

                OrderItem savedOrderItem=orderItemRepo.save(orderItem);
            }
        }
        PaymentOrder paymentOrder=new PaymentOrder();
        paymentOrder.setOrders(orders);
        paymentOrder.setAmount(totalOrderValue);
        paymentOrder.setUser(user);
        paymentOrder.setStatus(PaymentOrderStatus.PENDING);
        paymentOrderRepo.save(paymentOrder);
        return orders;


    }

    @Override
    public Order findOrderById(Long id,String jwt) throws Exception {

        Order order =orderRepo.findById(id).orElseThrow(()->new Exception("Order not found"));
        Long userId=userService.findUserByJwt(jwt).getId();
        if(!Objects.equals(userId, order.getUser().getId()))
            throw new Exception("No order found");

        return order;
    }

    @Override
    public Order findOrderByOrderId(Long id) throws Exception {
       return orderRepo.findById(id).orElseThrow(()->new Exception("Order not found"));
    }

    @Override
    public List<Order> usersOrderHistory(String jwt) throws Exception {

        Long userId= userService.findUserByJwt(jwt).getId();

        return orderRepo.findByUserId(userId);

    }

    @Override
    public List<Order> sellersOrder(String jwt) throws Exception {

        Long sellerId=sellerService.getSellerProfile(jwt).getId();

        return orderRepo.findBySellerId(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus,String jwt) throws Exception {

        Long sellerId=sellerService.getSellerProfile(jwt).getId();

        Order order=this.findOrderById(orderId,jwt);
        Long orderFromSellerId=order.getSellerId();

        if(!Objects.equals(sellerId, orderFromSellerId))
            throw new Exception("Unauthorised to modify order status");


        order.setOrderStatus(orderStatus);

        return orderRepo.save(order);



    }

    @Override
    public Order cancelOrder(Long orderId, String jwt) throws Exception {

        Long userId=userService.findUserByJwt(jwt).getId();
        Order order=this.findOrderById(orderId,jwt);
        Long orderFromUserId=order.getUser().getId();
        if(!Objects.equals(userId, orderFromUserId)) {
            throw  new Exception("Unauthorised to cancel order");

        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return  orderRepo.save(order);
    }

    @Override
    public OrderItem findOrderItemById(Long orderItemId,String jwt) throws Exception {

        OrderItem orderItem=orderItemRepo.findById(orderItemId).orElseThrow(()->new Exception("item not found"));

        Long userId=userService.findUserByJwt(jwt).getId();

        if(!Objects.equals(orderItem.getUserId(), userId))
        {
               throw  new Exception("OrderItem not found");
        }
            return  orderItem;
    }
}
