package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}
