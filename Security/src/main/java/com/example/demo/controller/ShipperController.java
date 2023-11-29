package com.example.demo.controller;

import com.example.demo.models.Order;
import com.example.demo.models.OrderItem;
import com.example.demo.models.Shipper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShiperRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ShipperController {
    @Autowired
    ShiperRepository shiperRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping("/admin/shipper")
    public String Shipper(Model model){
        model.addAttribute("shippers",shiperRepository.findAll());
        return "shipperManage";
    }
    @GetMapping("/admin/addshipper")
    public String AddShipper(Model model){
        model.addAttribute("shipper",new Shipper());
        return "addShipper";
    }
    @PostMapping("/admin/addshipper")
    public String PostShipper(@ModelAttribute("shipper") Shipper shipper){
        shipper.setPassword(passwordEncoder.encode(shipper.getPasswordBase()));
        shipper.setRole("ROLE_SHIPPER");
        shipper.setLastes(LocalDateTime.now());
        shiperRepository.save(shipper);
        return "redirect:/admin/shipper";
    }
    @GetMapping("/shipper/order")
    public String ShipperOrder(Model model, HttpSession session){
        String username = session.getAttribute("username1").toString();
        Shipper shipper = shiperRepository.findByUsername(username);
        List<Order> orders = orderRepository.findAllByShipperAndStatusOrderByCreatedAtAsc(shipper,"Đang chờ lấy hàng");
        List<Order> orders1 = orderRepository.findAllByShipperAndStatusOrderByCreatedAtAsc(shipper,"Dang giao hàng");
        orders.addAll(orders1);
        Collections.sort(orders, Comparator.comparing(Order::getCreatedAt));
        model.addAttribute("orders",orders);
        return "shipperorder";
    }
    @GetMapping("/shipper/orderdetail/{id}")
    public String Ordership(@PathVariable("id") Long id,Model model){
        Order order = orderRepository.getById(id);
        model.addAttribute("order",order);
        double total = 0;
        for( OrderItem orderItem : order.getOrderItems()){
            total += orderItem.getPrice_curent() * orderItem.getQuantity();
        }
        model.addAttribute("total",total);
        List<String> orderStatusList = new ArrayList<>();
        orderStatusList.add("Đang chờ lấy hàng");
        orderStatusList.add("Đang giao hàng");
        orderStatusList.add("Giao hàng thành công");
        orderStatusList.add("Giao hàng không thành công");
        model.addAttribute("orderStatusList",orderStatusList);
        return "orderdetailship";
    }
    @PostMapping("/shipper/status")
    public String PostStatus(@RequestParam("orderId") Long id,
                             @RequestParam("status") String status,
                             @RequestParam("notes") String note,
                             RedirectAttributes redirectAttributes){
        Order order = orderRepository.getById(id);
        order.setNote(note);
        if(status.equals("Đang chờ lấy hàng")){
            order.setStatus("Đang chờ lấy hàng");
        }
        if(status.equals("Đang giao hàng")){
            order.setStatus("Đang giao hàng");
        }
        if(status.equals("Giao hàng thành công")){
            order.setStatus("Giao hàng thành công");
        }if(status.equals("Giao hàng không thành công")){
            order.setStatus("Giao hàng không thành công");
        }
        orderRepository.save(order);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/shipper/orderdetail/{id}";
    }
    @GetMapping("/shipper/success")
    public String ShipperSuccess(Model model, HttpSession session){
        String username = session.getAttribute("username1").toString();
        Shipper shipper = shiperRepository.findByUsername(username);
        List<Order> orders = orderRepository.findAllByShipperAndStatusOrderByCreatedAtAsc(shipper,"Giao hàng thành công");
        List<Order> orders1 = orderRepository.findAllByShipperAndStatusOrderByCreatedAtAsc(shipper,"Giao hàng không thành công");
        orders.addAll(orders1);
        Collections.sort(orders, Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("orders",orders);
        return "shipperorder";
    }
}
