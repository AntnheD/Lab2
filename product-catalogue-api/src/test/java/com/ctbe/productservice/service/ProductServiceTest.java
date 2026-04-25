package com.ctbe.productservice.service;

import com.ctbe.productservice.model.Product;
import com.ctbe.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product(1L, "Laptop", "Gaming Laptop", new BigDecimal("1200.00"));
        product2 = new Product(2L, "Mouse", "Wireless Mouse", new BigDecimal("25.50"));
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Optional<Product> found = productService.getProductById(1L);

        assertTrue(found.isPresent());
        assertEquals("Laptop", found.get().getName());
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> found = productService.getProductById(99L);

        assertFalse(found.isPresent());
    }

    @Test
    void createProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product created = productService.createProduct(product1);

        assertNotNull(created);
        assertEquals("Laptop", created.getName());
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void updateProduct_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product updateDetails = new Product(null, "Updated Laptop", "Updated Desc", new BigDecimal("1500.00"));
        Optional<Product> updated = productService.updateProduct(1L, updateDetails);

        assertTrue(updated.isPresent());
        assertEquals("Updated Laptop", updated.get().getName());
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void deleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
