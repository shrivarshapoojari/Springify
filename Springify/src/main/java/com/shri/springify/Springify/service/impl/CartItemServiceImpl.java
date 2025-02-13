package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.model.CartItem;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.CartItemRepo;
import com.shri.springify.Springify.service.CartItemService;
import com.shri.springify.Springify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;


    @Autowired
    private UserService userService;

    @Override
    public CartItem updateCartItem(String jwt, Long id, CartItem cartItem) throws Exception {

         CartItem item=this.findCartItemById(id);
        Long cartUserId= item.getCart().getUser().getId();

        Long requestUserId=userService.findUserByJwt(jwt).getId();

        if(!Objects.equals(cartUserId, requestUserId))
            throw new Exception("Unauthorised to update cart");

        item.setQuantity(cartItem.getQuantity());
        item.setMrpPrice(item.getMrpPrice()*cartItem.getQuantity());
        item.setSellingPrice(item.getQuantity()* item.getSellingPrice());


        return  cartItemRepo.save(cartItem);





    }

    @Override
    public void removeCartItem(String jwt, Long id) throws Exception {


        CartItem item=this.findCartItemById(id);
        Long cartUserId= item.getCart().getUser().getId();

        Long requestUserId=userService.findUserByJwt(jwt).getId();

        if(!Objects.equals(cartUserId, requestUserId))
            throw new Exception("Unauthorised to delete cart");


        cartItemRepo.delete(item);
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
         return cartItemRepo.findById(id).orElseThrow(()->
                 new Exception("Cart not found"));
    }
}
