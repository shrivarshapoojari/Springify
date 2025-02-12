package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findByCategoryId(String categoryId);
}
