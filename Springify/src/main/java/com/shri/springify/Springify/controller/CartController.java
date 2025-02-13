package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.model.Cart;
import com.shri.springify.Springify.model.CartItem;
import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.model.User;
import com.shri.springify.Springify.response.AddItemRequest;
import com.shri.springify.Springify.response.ApiResponse;
import com.shri.springify.Springify.service.CartItemService;
import com.shri.springify.Springify.service.CartService;
import com.shri.springify.Springify.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;



    @GetMapping()
    public ResponseEntity<Cart>findUsersCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Cart cart=cartService.findUsersCart(jwt);
        return  new ResponseEntity<>(cart, HttpStatus.OK);
    }





    @PutMapping("/add")
    public  ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
         Product product=productService.findProductById(req.getProductId());

        CartItem item=cartService.addCartItem(jwt,product,req.getSize(),req.getQuantity());

        return new ResponseEntity<>(item,HttpStatus.OK);
    }




    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        ApiResponse response=new ApiResponse();
        cartItemService.removeCartItem(jwt,cartItemId);

        response.setMessage("item removed successfully");

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("/item/{cartItemId}")
    public  ResponseEntity<CartItem>updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        CartItem updatedCartItem=null;
        if(cartItem.getQuantity()>0)
        {
            updatedCartItem=cartItemService.updateCartItem(jwt,cartItemId,cartItem);
            return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
        }
        throw  new Exception("Quantity cant be negative");
    }
}
