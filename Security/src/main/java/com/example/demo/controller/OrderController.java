package com.example.demo.controller;

import com.example.demo.models.Order;
import com.example.demo.models.OrderItem;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @GetMapping("admin/order")
    public String Order(Model model){
        model.addAttribute("orders",orderRepository.findAll());
        return "order";
    }
    @GetMapping("/admin/orderdetail/{id}")
    public String OrderDetail(@PathVariable("id") Long id, Model model){
        Order order = orderRepository.getById(id);
        model.addAttribute("order",order);
        double total = 0;
        for( OrderItem orderItem : order.getOrderItems()){
            total += orderItem.getPrice_curent() * orderItem.getQuantity();
        }
        model.addAttribute("total",total);
        return "orderdetail";
    }
}
