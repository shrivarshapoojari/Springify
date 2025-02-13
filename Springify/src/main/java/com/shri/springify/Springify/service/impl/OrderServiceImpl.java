package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.domain.OrderStatus;
import com.shri.springify.Springify.domain.PaymentStatus;
import com.shri.springify.Springify.model.*;
import com.shri.springify.Springify.repository.AddressRepo;
import com.shri.springify.Springify.repository.OrderItemRepo;
import com.shri.springify.Springify.repository.OrderRepo;
import com.shri.springify.Springify.service.OrderService;
import com.shri.springify.Springify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    UserService userService;


    @Autowired
    AddressRepo addressRepo;

    @Autowired
    OrderItemRepo orderItemRepo;

    @Override
    public Set<Order> createOrder(String jwt, Address shippingAddress, Cart cart) throws Exception {
        User user=userService.findUserByJwt(jwt);

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
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setShippingAddress(address);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.setPaymentStatus(PaymentStatus.PENDING);

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

        return orders;


    }

    @Override
    public Order findOrderById(Long id) {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(String jwt) {
        return List.of();
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return List.of();
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        return null;
    }

    @Override
    public Order cancelOrder(Long orderId, String jwt) {
        return null;
    }
}
