package com.example.demo.controller;

import com.example.demo.models.*;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShiperRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class BillController {
    @Autowired
    ShiperRepository shiperRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartRepository cartRepository;
    @GetMapping("/customer/payment")
    public String Bill(Model model){
        model.addAttribute("order",new Order());
        return "bill";
    }
    @PostMapping("/customer/payment")
    public String BillPost(@ModelAttribute("order") Order order,Model model, HttpSession session){
        Cart cart =(Cart) session.getAttribute("cart");
        order.setUser(cart.getUser());
        order.setPayment("COD");
        order.setCreatedAt(LocalDateTime.now());
        order.setPrice_ship(30);
        order.setStatus("Đang chờ lấy hàng");
        Shipper shipper = shiperRepository.findShipperWithOldestLastes();
        order.setShipper(shipper);
        order = orderRepository.save(order);
        for(CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice_curent(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        orderRepository.save(order);
        cartRepository.delete(cart);
        shipper.setLastes(order.getCreatedAt());
        shiperRepository.save(shipper);
        session.removeAttribute("cart");
        Double total  = (Double) session.getAttribute("total");
        session.removeAttribute("total");
        model.addAttribute("total",total);
        model.addAttribute("order",order);
        return "confirm";
    }
    @GetMapping("/customer/paypal")
    public String BillPaypal(@RequestParam("receiverName") String receiverName,
            @RequestParam("receiverPhone") String receiverPhone,
            @RequestParam("receiverAddress") String receiverAddress,Model model, HttpSession session){
        Cart cart =(Cart) session.getAttribute("cart");
        Order order = new Order();
        order.setReciverName(receiverName);
        order.setReciverPhone(receiverPhone);
        order.setReciverAddress(receiverAddress);
        order.setUser(cart.getUser());
        order.setPayment("Paypal");
        order.setCreatedAt(LocalDateTime.now());
        order.setPrice_ship(30);
        order.setStatus("Đang chờ lấy hàng");
        Shipper shipper = shiperRepository.findShipperWithOldestLastes();
        order.setShipper(shipper);
        order = orderRepository.save(order);
        for(CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice_curent(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        orderRepository.save(order);
        cartRepository.delete(cart);
        shipper.setLastes(order.getCreatedAt());
        shiperRepository.save(shipper);
        session.removeAttribute("cart");
        Double total  = (Double) session.getAttribute("total");
        session.removeAttribute("total");
        model.addAttribute("total",total);
        model.addAttribute("order",order);
        return "confirm";
    }
}
