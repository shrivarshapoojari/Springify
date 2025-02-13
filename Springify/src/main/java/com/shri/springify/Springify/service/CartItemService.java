package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.CartItem;

public interface CartItemService {

    CartItem updateCartItem(String jwt,Long id,CartItem cartItem) throws Exception;
    void removeCartItem(String jwt,Long cartItemId) throws Exception;
    CartItem findCartItemById(Long id) throws Exception;
}
