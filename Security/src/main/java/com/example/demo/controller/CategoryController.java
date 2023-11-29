package com.example.demo.controller;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.Size;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SizeRepository sizeRepository;
    @GetMapping("/customer/category/{id}")
    public String Category(@PathVariable("id") Long id, Model model){
        Category category = categoryRepository.getById(id);
        List<Product> products = productRepository.findAllByCategory(category);
        model.addAttribute("products",products);
        return "home";
    }
    @GetMapping("/customer/size/{id}")
    public String Size(@PathVariable("id") Long id, Model model){
        Size size = sizeRepository.getById(id);
        List<Product> products = productRepository.findAllBySize(size);
        model.addAttribute("products",products);
        return "home";
    }
    @GetMapping("/customer/size")
    public String Tínhize(){
        return "sizeCaculator";
    }
    @PostMapping("/customer/size")
    public String PostSize(@RequestParam("length") Double length,Model model,
                           @RequestParam("stand") String stand){
        Double size = 0.0;
        List<Product> products = new ArrayList<>();
        if(stand.equals("EU")) {
            size = (length + 1.5) * 3 / 2;
            DecimalFormat decimalFormat = new DecimalFormat("#");
            double roundedSize = Double.parseDouble(decimalFormat.format(size));
            System.out.println(roundedSize);
            products = productRepository.findAllBySize(sizeRepository.findBySizeEu(roundedSize)) ;
        }
        if(stand.equals("US")) {
            size = (length/2.54 * 3) - 22;
            DecimalFormat decimalFormat = new DecimalFormat("#");
            double roundedSize = Double.parseDouble(decimalFormat.format(size));
            System.out.println(roundedSize);
            products = productRepository.findAllBySize(sizeRepository.findBySizeUs(roundedSize)) ;
        }

        model.addAttribute("products",products);
        return "home";
    }
}
