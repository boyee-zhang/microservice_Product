package com.product.ProductService.service;
import com.product.ProductService.model.Product;
import java.util.*;
public interface ProductService {
    Product createProduct(Product product);

    Product getProductById(UUID id);

    List<Product> getProductsByName(String name);

    void deleteProductById(UUID id);

    Product updateProductById(Product product);
}
