package com.example.project_api.domain;

import com.example.project_api.domain.product.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductCreation() {
        Product laptop = new Product(1L, "Laptop", 1500.00);
        assertEquals(1L, laptop.getId());
        assertEquals("Laptop", laptop.getName());
        assertEquals(1500.00, laptop.getPrice());
    }

    @Test
    public void testApplyDiscount() throws Exception {
        Product laptop = new Product(1L, "Laptop", 1500.00);
        laptop.applyDiscount(10);

        assertThrows(IllegalArgumentException.class, () -> {
           laptop.applyDiscount(-5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            laptop.applyDiscount(105);
        });
    }
}
