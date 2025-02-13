package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.Cart;
import com.shri.springify.Springify.model.CartItem;
import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.model.User;

public interface CartService {


    public CartItem addCartItem(String jwt, Product product, String size, int quantity) throws Exception;

    public Cart findUsersCart(String jwt) throws Exception;

}
