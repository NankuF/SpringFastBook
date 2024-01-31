package ru.poltoranin.getandpost.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.poltoranin.getandpost.models.Product;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    public List<Product> findAll() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
