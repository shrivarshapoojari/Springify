package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.model.CartItem;
import com.shri.springify.Springify.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Override
    public CartItem updateCartItem(String jwt, Long id, CartItem cartItem) {
        return null;
    }

    @Override
    public void removeCartItem(String jwt, Long cartItemId) {

    }

    @Override
    public CartItem findCartItemById(Long id) {
        return null;
    }
}
