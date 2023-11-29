package com.example.demo.ModulAddToCartTest;


import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
@DataJpaTest
public class findByUserNameUserTest {
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    public void Setup(){
        User user = User.builder().username("username1").build();
        User user2 = User.builder().username("u2").build();

        testEntityManager.persist(user);
        testEntityManager.persist(user2);
    }
    @Test
    public void testFindByUserName() {
        User foundUser = userRepository.findByUsername("username1");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("username1");
    }
    @Test
    public void testfindByUserNamereturnNull(){
        User founduser = userRepository.findByUsername("user");
        System.out.println(founduser);
        assertThat(founduser).isNull();
    }
    @After
    public void Clean(){
        testEntityManager.clear();
    }
}
