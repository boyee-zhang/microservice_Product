package com.product.ProductService.service;

import com.product.ProductService.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    void testCreateProduct()
    {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(200.0));
        product.setName("test product");

        Product saved = productService.createProduct(ghproduct);

        assertNotNull(saved.getId());
        assertEquals("test product", saved.getName());
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setName("sample");
        product.setPrice(BigDecimal.valueOf(99.99));

        Product saved = productService.createProduct(product);
        Product found = productService.getProductById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals("sample", found.getName());
    }

    @Test
    void testGetProductsByName() {
        String name = "testName_" + UUID.randomUUID();

        Product product1 = new Product();
        product1.setName(name);
        product1.setPrice(BigDecimal.valueOf(10));
        product1.setQuantity(1); // 添加必要字段
        product1.setCreatedAt(LocalDateTime.now());
        product1.setUpdatedAt(LocalDateTime.now());

        Product product2 = new Product();
        product2.setName(name);
        product2.setPrice(BigDecimal.valueOf(20));
        product2.setQuantity(1);
        product2.setCreatedAt(LocalDateTime.now());
        product2.setUpdatedAt(LocalDateTime.now());

        productService.createProduct(product1);
        productService.createProduct(product2);

        var result = productService.getProductsByName(name);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateProductById() {
        Product product = new Product();
        product.setName("Old Name");
        product.setPrice(BigDecimal.valueOf(50));
        Product saved = productService.createProduct(product);

        saved.setName("New Name");
        saved.setPrice(BigDecimal.valueOf(75));

        Product updated = productService.updateProductById(saved);

        assertEquals("New Name", updated.getName());
        assertEquals(BigDecimal.valueOf(75), updated.getPrice());
    }

    @Test
    void testDeleteProductById() {
        Product product = Product.builder()
                .name("To Be Deleted")
                .price(BigDecimal.valueOf(33.3))
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product saved = productService.createProduct(product);

        productService.deleteProductById(saved.getId());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(saved.getId());
        });

        assertEquals("Product not found with id: " + saved.getId(), exception.getMessage());
    }

}
