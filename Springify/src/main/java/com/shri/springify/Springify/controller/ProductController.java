package com.shri.springify.Springify.controller;

import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductService productService;



    @GetMapping("/search")
    public  ResponseEntity<List<Product>> searchProduct(@RequestParam String query)
    {
        List<Product> products=productService.searchProducts(query);
        return  new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required=false) String category,
            @RequestParam(required=false) String brand,
            @RequestParam(required=false) String color,
            @RequestParam(required=false) String size,
            @RequestParam(required=false) Integer minPrice ,
            @RequestParam(required=false) Integer maxPrice,
            @RequestParam(required=false) Integer minDiscount,
            @RequestParam(required=false) String sort,
            @RequestParam(required=false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber
    )
    {
        return new ResponseEntity<>(productService.getAllProducts(category,brand,color,size,maxPrice,minPrice,minDiscount,sort,stock,pageNumber),HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws Exception {
        Product product=productService.findProductById(productId);

        return  new ResponseEntity<>(product, HttpStatus.OK);
    }


}
