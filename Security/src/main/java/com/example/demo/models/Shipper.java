package com.example.demo.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity

public class Shipper extends User{
    private LocalDateTime lastes;
    @OneToMany(mappedBy = "shipper",cascade = CascadeType.ALL)
    private List<Order> orders;

    public LocalDateTime getLastes() {
        return lastes;
    }

    public void setLastes(LocalDateTime lastes) {
        this.lastes = lastes;
    }
}
