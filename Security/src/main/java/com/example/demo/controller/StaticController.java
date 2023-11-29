package com.example.demo.controller;

import com.example.demo.models.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class StaticController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @GetMapping("/admin/statics")
    public String Statics(Model model){
        List<User> users = userRepository.findAllByRole("ROLE_USER");
        List<TKDTTKhachHang> tkdttKhachHangs = new ArrayList<>();
        for(User user : users){
            System.out.println(user);
            TKDTTKhachHang tkdttKhachHang = new TKDTTKhachHang();
                tkdttKhachHang.setFullname(user.getFullname());
                tkdttKhachHang.setAddress(user.getAddress());
                tkdttKhachHang.setEmail(user.getEmail());
                tkdttKhachHang.setId(user.getId());
                tkdttKhachHang.setPhone(user.getPhone());
                List<Order> orders = orderRepository.findAllByUser(user);
                double doanhthu = 0;
                for (Order order : orders) {
                    for (OrderItem orderItem : order.getOrderItems()) {
                        doanhthu += orderItem.getQuantity() * orderItem.getPrice_curent();
                    }
                    doanhthu += order.getPrice_ship();
                }
                tkdttKhachHang.setDoanhthu(doanhthu);
                tkdttKhachHangs.add(tkdttKhachHang);

        }
        Collections.sort(tkdttKhachHangs, Comparator.comparing(TKDTTKhachHang::getDoanhthu).reversed());
        model.addAttribute("tkdttkhs",tkdttKhachHangs);
        return "tkdttkh";
    }
    @GetMapping("/admin/staticbytime")
    public String StaPro(){
        return "tkdtttg";
    }
    @PostMapping("/admin/staticbytime")
    public String PostTime(@RequestParam("start") LocalDate start,
                           @RequestParam("finish") LocalDate finish,
                           Model model){
        List<TKDTTNgay> tkdttNgays = new ArrayList<>();
        LocalDate current = start;
        while(!current.isAfter(finish)){
            TKDTTNgay tkdttNgay = new TKDTTNgay();
            tkdttNgay.setDay(current);
            List<Order> orders = orderRepository.findAllByCreatedAtBetween(current.atStartOfDay(),current.atTime(LocalTime.MAX));
            double doanhthu = 0;
            for (Order order : orders) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    doanhthu += orderItem.getQuantity() * orderItem.getPrice_curent();
                }
                doanhthu += order.getPrice_ship();
            }
            tkdttNgay.setDoanhthu(doanhthu);
            tkdttNgays.add(tkdttNgay);
            current = current.plusDays(1);
        }
        model.addAttribute("tkdttNgays",tkdttNgays);
        return "tkdtttg";
    }
}
