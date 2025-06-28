package com.product.ProductService.service;
import com.product.ProductService.repository.*;
import com.product.ProductService.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product createProduct(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> result = productRepository.findByNameContainingIgnoreCase(name);
        return result;
    }

    @Override
    public Product updateProductById(Product product) {
        UUID id = product.getId();

        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null for update");
        }

        Product updatedProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update, because database can't find a product id: " + id));

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setQuantity(product.getQuantity());
        LocalDateTime now = LocalDateTime.now();
        updatedProduct.setUpdatedAt(now);

        return updatedProduct;
    }

    @Override
    public void deleteProductById(UUID id) {
        if(!productRepository.existsById(id))
        {
            throw  new RuntimeException("Cannot delete: Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }
}
