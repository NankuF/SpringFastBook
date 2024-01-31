package ru.poltoranin.getandpost.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.poltoranin.getandpost.models.Product;
import ru.poltoranin.getandpost.services.ProductService;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ProductsController {

    private final ProductService productService;
    
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String viewProducts(Model page) {
        var products = productService.findAll();
        page.addAttribute("products", products);
        return "products.html";
    }

    @PostMapping("/products")
    public String addProduct(@RequestParam String name, @RequestParam double price, Model model) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        productService.addProduct(product);

        var products = productService.findAll();
        model.addAttribute("products", products);

        return "products.html";
    }


}
