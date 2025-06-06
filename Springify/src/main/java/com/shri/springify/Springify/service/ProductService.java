package com.shri.springify.Springify.service;

import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.response.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req, Seller seller);



    void deleteProduct(Long productId, Long sellerId) throws Exception;

    public Product updateProduct(Long productId, Product product,Long sellerId) throws Exception;

    Product findProductById(Long id) throws Exception;



    List<Product> searchProducts(String query);

    public Page<Product> getAllProducts(

            String category,
            String brand,
            String colors,
            String size,
            Integer maxPrice,
            Integer minPrice,
            Integer minDiscount,
            String sort,

            String stock,
            Integer pageNumber
    );


    List<Product> getProductBySellerId(Long id);

}
