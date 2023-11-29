package com.example.demo.ModulAddToCartTest;

import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class findCartByUserTest {
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CartRepository cartRepository;

}
