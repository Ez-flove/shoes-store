package com.example.demo.controller;

import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartRepository cartRepository;
    @GetMapping("/customer/cart")
    public String GetCart(HttpSession session, Model model){
        User user = userRepository.findByUsername(session.getAttribute("username1").toString());
        Cart cart = cartRepository.findCartByUser(user);
        if(cart==null){
            model.addAttribute("notify","Không có sản phẩm nào trong giỏ hàng");
            return "cart";
        }
        double total= 0.0;
        for(CartItem cartItem : cart.getCartItems()){
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        session.setAttribute("cart",cart);
        session.setAttribute("total",total);
        return "cart";
    }
    @PostMapping("/customer/cart")
    public String PostCart(@RequestParam("productId") Long id, @RequestParam("quantity") int sl
    , HttpSession session){
        Product product = productRepository.getById(id);
        System.out.println(product);
        User user = userRepository.findByUsername(session.getAttribute("username1").toString());
        Cart cart = cartRepository.findCartByUser(user);
        System.out.println("cart: " +cart);
        if (cart == null) {
            cart = new Cart();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(sl);
            cartItem.setCart(cart);
            cart.setUser(user);
            cart.getCartItems().add(cartItem);
            cartRepository.save(cart);

            return "redirect:/customer/cart";
        }
        CartItem cartItem = cartItemRepository.findCartItemByProductAndCart( product,cart);
        System.out.println("chien "+ cartItem );
        if(cartItem == null){
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(sl);
            cart.AddtoCart(cartItem);
            cartRepository.save(cart);
            session.setAttribute("cart",cart);
            return "redirect:/customer/cart";
        }
        cartItem.setQuantity(cartItem.getQuantity()+sl);
        cartItemRepository.save(cartItem);
        return "redirect:/customer/cart";

    }
    @PostMapping("/customer/updatequantity")
    public String UpdateQuantity(@RequestParam("itemId") Long id, @RequestParam("quantity") int sl){
        CartItem cartItem = cartItemRepository.getById(id);
        cartItem.setQuantity(sl);
        cartItemRepository.save(cartItem);
        return  "redirect:/customer/cart";
    }
    @GetMapping("/customer/deleteitem/{id}")
    public String DeleteItem(@PathVariable("id") Long id){
        cartItemRepository.deleteById(id);
        return  "redirect:/customer/cart";
    }
}
