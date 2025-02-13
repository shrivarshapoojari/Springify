package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.CartItem;

public interface CartItemService {

    CartItem updateCartItem(String jwt,Long id,CartItem cartItem);
    void removeCartItem(String jwt,Long cartItemId);
    CartItem findCartItemById(Long id);
}
