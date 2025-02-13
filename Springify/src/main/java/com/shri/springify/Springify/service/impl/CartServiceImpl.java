package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.model.Cart;
import com.shri.springify.Springify.model.CartItem;
import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.repository.CartItemRepo;
import com.shri.springify.Springify.repository.CartRepo;
import com.shri.springify.Springify.service.CartService;
import com.shri.springify.Springify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private CartRepo cartRepo;

    @Override
    public CartItem addCartItem(String jwt, Product product, String size, int quantity) throws Exception {
         Cart cart=this.findUsersCart(jwt);

         CartItem isPresent=cartItemRepo.findByCartAndProductAndSize(cart,product,size);

         if(isPresent==null)
         {
             CartItem cartItem=new CartItem();
             cartItem.setProduct(product);
             cartItem.setQuantity(quantity);
             cartItem.setCart(cart);
             cartItem.setSize(size);
             int totalSellingPrice= (int) (quantity*product.getSellingPrice());
             cartItem.setSellingPrice(totalSellingPrice);
             int totalMrpPrice= (int) (quantity*product.getMrpPrice());
             cartItem.setMrpPrice(totalMrpPrice);

             cart.getCartItems().add(cartItem);
             return  cartItemRepo.save(cartItem);
         }
         else{
             cart.getCartItems().remove(isPresent);
                int presentQuantity= isPresent.getQuantity();
                int updatedItem=presentQuantity+quantity;
                isPresent.setQuantity(updatedItem);

             int totalSellingPrice= (int) (updatedItem*product.getSellingPrice());
             isPresent.setSellingPrice(totalSellingPrice);
             int totalMrpPrice= (int) (updatedItem*product.getMrpPrice());
             isPresent.setMrpPrice(totalMrpPrice);
             cart.getCartItems().add(isPresent);
             return  cartItemRepo.save(isPresent);

         }


        return null;
    }

    @Override
    public Cart findUsersCart(String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);
        Cart cart=cartRepo.findByUserId(user.getId());

        int totalPrice=0;
        int totalDiscountPrice=0;
        int totalItem=0;

        for(CartItem cartItem:cart.getCartItems())
        {
            totalPrice+=cartItem.getMrpPrice();
            totalDiscountPrice+=cartItem.getSellingPrice();
            totalItem+=cartItem.getQuantity();
        }
        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalSellingPrice(totalDiscountPrice);
        cart.setDiscount((totalPrice-totalDiscountPrice)/100);
        cart.setTotalItem(totalItem);
        return null;
    }
}
