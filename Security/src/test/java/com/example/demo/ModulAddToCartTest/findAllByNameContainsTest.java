package com.example.demo.ModulAddToCartTest;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
public class findAllByNameContainsTest {
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    ProductRepository productRepository;
    @BeforeEach
    public void Setup(){
        Product product1 = new Product();
        product1.setName("product1");
        testEntityManager.flush();
        testEntityManager.persist(product1);
        Product product2 = new Product();
        product2.setName("product2");
        testEntityManager.flush();
        testEntityManager.persist(product2);
        Product product3 = new Product();
        product3.setName("sanpham3");
        testEntityManager.flush();
        testEntityManager.persist(product3);
    }
    @Test
    public void findAllByContainsTest(){
        List<Product> products = productRepository.findAllByNameContains("pro");
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("product1");
        assertThat(products.get(1).getName()).isEqualTo("product2");
    }
    @Test
    public void findAllByContainsTestReturnNull(){
        List<Product> products = productRepository.findAllByNameContains("productnull");
        assertThat(products).isEmpty();
    }
    @After
    public void Clean(){
        testEntityManager.clear();
    }
}
