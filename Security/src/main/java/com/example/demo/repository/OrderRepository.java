package com.example.demo.repository;

import com.example.demo.models.Order;
import com.example.demo.models.Shipper;
import com.example.demo.models.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByShipperAndStatusOrderByCreatedAtAsc(Shipper shipper,String statusorder);
    List<Order> findAllByUser(User user);
    List<Order> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime finish);
}
