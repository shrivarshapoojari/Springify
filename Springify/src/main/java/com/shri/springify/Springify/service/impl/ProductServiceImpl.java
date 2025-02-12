package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.model.Category;
import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.repository.CategoryRepo;
import com.shri.springify.Springify.repository.ProductRepo;
import com.shri.springify.Springify.response.CreateProductRequest;
import com.shri.springify.Springify.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl  implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;




    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {

        Category category1=categoryRepo.findByCategoryId(req.getCategory());

        if(category1==null)
        {
            Category category=new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1=categoryRepo.save(category);
        }
        Category category2=categoryRepo.findByCategoryId(req.getCategory2());
        if(category2==null)
        {
            Category category=new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2=categoryRepo.save(category);
        }
        Category category3=categoryRepo.findByCategoryId(req.getCategory3());
        if(category3==null)
        {
            Category category=new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3=categoryRepo.save(category);
        }


        int discount= (int) (req.getMrpPrice() - req.getSellingPrice()) /100;

        Product product=new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
         product.setColor(req.getColor());
         product.setSellingPrice(req.getSellingPrice());
product.setMrpPrice(req.getMrpPrice());
product.setImages(req.getImages());
product.setSize(req.getSize());
product.setDiscountPercent(discount);

return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public Product findProductById(Long id) {
        return null;
    }

    @Override
    public List<Product> searchProducts() {
        return List.of();
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String size, Integer maxPrice, Integer minPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        return null;
    }

    @Override
    public List<Product> getProductBySellerId(Long id) {
        return List.of();
    }
}
