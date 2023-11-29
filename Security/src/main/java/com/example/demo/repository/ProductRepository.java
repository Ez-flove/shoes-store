package com.example.demo.repository;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByNameContains(String name);

    List<Product> findAllByCategory(Category category);
    List<Product> findAllBySize(Size size);
}
