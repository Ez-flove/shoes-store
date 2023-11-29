package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "order099")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String reciverName;
    private String reciverPhone;
    private String reciverAddress;
    private String note;
    private double price_ship;
    private String status;
    private String payment;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;
    public Order(){
        orderItems = new ArrayList<>();
    }
}
