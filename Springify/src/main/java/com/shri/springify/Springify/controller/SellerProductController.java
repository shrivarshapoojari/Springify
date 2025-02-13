package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.response.ApiResponse;
import com.shri.springify.Springify.response.CreateProductRequest;
import com.shri.springify.Springify.service.ProductService;
import com.shri.springify.Springify.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers/products")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;





    @GetMapping()
    public ResponseEntity<List<Product>> getProductBySellerId(
            @RequestHeader("Authorization") String jwt
    ) throws  Exception
    {
        Seller seller=sellerService.getSellerProfile(jwt);
        List<Product> products=productService.getProductBySellerId(seller.getId());
        return  new ResponseEntity<>(products, HttpStatus.OK);
    }








    @PostMapping()
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        Seller seller=sellerService.getSellerProfile(jwt);

        Product product=productService.createProduct(request,seller);

        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }





    @DeleteMapping("/{productId}")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId,
    @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Seller seller=sellerService.getSellerProfile(jwt);
       ApiResponse response=new ApiResponse();

        productService.deleteProduct(productId,seller.getId());
        response.setMessage("Product deleted successfully");

        return  new ResponseEntity<>(response,HttpStatus.OK);
    }




    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Product product
    ) throws Exception {
        Seller seller=sellerService.getSellerProfile(jwt);

        Product updatedProduct=productService.updateProduct(productId,product,seller.getId());
        return  new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

}
