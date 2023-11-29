package com.example.demo.repository;

import com.example.demo.models.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiperRepository extends JpaRepository<Shipper,Long> {
    @Query("SELECT s FROM Shipper s WHERE s.lastes = (SELECT MIN(s2.lastes) FROM Shipper s2)")
    Shipper findShipperWithOldestLastes();
    Shipper findByUsername(String username);
}
