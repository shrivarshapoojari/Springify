package com.shri.springify.Springify.service.impl;

import com.shri.springify.Springify.model.Category;
import com.shri.springify.Springify.model.Product;
import com.shri.springify.Springify.model.Seller;
import com.shri.springify.Springify.repository.CategoryRepo;
import com.shri.springify.Springify.repository.ProductRepo;
import com.shri.springify.Springify.response.CreateProductRequest;
import com.shri.springify.Springify.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public void deleteProduct(Long productId, Long sellerId) throws Exception {

        Product product=this.findProductById(productId);
        Long creatorId=product.getSeller().getId();
        if(!Objects.equals(creatorId, sellerId))
            throw new Exception("You are not authorised to delete this product");
        productRepo.delete(product);

    }

    @Override
    public Product updateProduct(Long productId, Product product,Long sellerId) throws Exception {
        Product product1=this.findProductById(productId);
        Long creatorId=product1.getSeller().getId();
        if(!Objects.equals(creatorId, sellerId))
            throw new Exception("You are not authorised to delete this product");
      this.findProductById(productId);
        product.setId(productId);
        return productRepo.save(product);
    }

    @Override
    public Product findProductById(Long id) throws Exception {

        Optional<Product>product=productRepo.findById(id);

        if(product.isEmpty())
            throw new Exception("Product not found");


        return product.get();

    }

    @Override
    public List<Product> searchProducts(String query) {
         return productRepo.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String size, Integer maxPrice, Integer minPrice, Integer minDiscount, String sort, String stock, Integer pageNumber)

    {

        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Category filter
            if (category != null) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), category));
            }

            // Brand filter
            if (brand != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), brand));
            }

            // Color filter
            if (colors != null) {
                predicates.add(criteriaBuilder.equal(root.get("color"), colors));
            }

            // Size filter
            if (size != null) {
                predicates.add(criteriaBuilder.equal(root.get("size"), size));
            }

            // Price range filter
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
            }

            // Minimum discount filter
            if (minDiscount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercentage"), minDiscount));
            }

            if(stock!=null)
                predicates.add(criteriaBuilder.equal(root.get("stock"),stock));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };


        Sort sorting = Sort.unsorted();
        if (sort != null) {
            if (sort.equalsIgnoreCase("priceAsc")) {
                sorting = Sort.by(Sort.Order.asc("sellingPrice"));
            } else if (sort.equalsIgnoreCase("priceDesc")) {
                sorting = Sort.by(Sort.Order.desc("sellingPrice"));
            } else if (sort.equalsIgnoreCase("discountDesc")) {
                sorting = Sort.by(Sort.Order.desc("discountPercentage"));
            }
        }

        Pageable pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10);
        return productRepo.findAll(spec, pageable);
    }

    @Override
    public List<Product> getProductBySellerId(Long id) {
        return productRepo.findBySellerId(id);

    }
}
