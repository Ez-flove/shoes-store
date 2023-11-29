package com.example.demo.controller;

import com.example.demo.models.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin
@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SizeRepository sizeRepository;
    @GetMapping("/admin/product")
    public String GetAllProduct(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products",products);
        return "product";
    }
    @GetMapping("/admin/addproduct")
    public String AddProduct(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("sizes",sizeRepository.findAll());
        return "addProduct";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/addproduct")
    public String AddProductPost(@ModelAttribute("product") Product product, Model model){
        Product productSave = productRepository.save(product);
        model.addAttribute("res","AddProduct Sucessful");
        model.addAttribute("product",productSave);
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("sizes",sizeRepository.findAll());
        return "addProduct";
    }
    @GetMapping("/admin/editproduct/{id}")
    public String Update(@PathVariable("id") Long id,Model model){
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("product",productRepository.getById(id));
        model.addAttribute("sizes",sizeRepository.findAll());

        return "updateProduct";
    }
    @PostMapping("/admin/editproduct/{id}")
    public String UpdatePost(@PathVariable("id") Long id, Model model, @ModelAttribute("product") Product product){
        Product productsave = productRepository.getOne(id);
        productsave.setName(product.getName());
        productsave.setCategory(product.getCategory());
        productsave.setPrice(product.getPrice());
        productsave.setImage(product.getImage());
        productsave.setSize(product.getSize());
        productRepository.save(productsave);
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("product",productRepository.getById(id));
        model.addAttribute("sizes",sizeRepository.findAll());
        model.addAttribute("res","Cập nhật thành công");
        return "updateProduct";
    }
    @GetMapping("/admin/deleteproduct/{id}")
    public String Delete(@PathVariable("id") Long id){
        productRepository.deleteById(id);
        return "redirect:/admin/product";
    }
    @GetMapping("/customer/product/{id}")
    public  String Detail(@PathVariable("id") Long id, Model model){
        model.addAttribute("product",productRepository.getById(id));
        return "productDetail";
    }
    @PostMapping ("/customer/searchproduct")
    public String SearchProduct(@RequestParam("ProductName") String name,Model model){
        model.addAttribute("products",productRepository.findAllByNameContains(name));
        return "home";
    }
}
