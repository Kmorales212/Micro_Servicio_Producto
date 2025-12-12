package com.example.demo.testUnitarios;

import com.example.demo.model.ProductoModel; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoModelTest {

    private final String baseUrl = "http://localhost:8080/images/";

    @Test
    void testAllArgsConstructorAndGetters() {
        Long id = 1L;
        String name = "Pastel de Chocolate";
        double price = 18500.0;
        int stock = 10;
        String url = baseUrl + "pastel_chocolate.webp";

        ProductoModel producto = new ProductoModel(id, name, price, stock, url);

        assertEquals(id, producto.getId());
        assertEquals(name, producto.getName());
        assertEquals(price, producto.getPrice());
        assertEquals(stock, producto.getStock());
        assertEquals(url, producto.getImagenUrl());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        ProductoModel producto = new ProductoModel();
        
        producto.setId(2L);
        producto.setName("Pastel de fresa");
        producto.setPrice(15990.0);
        producto.setStock(15);
        producto.setImagenUrl(baseUrl + "pastel_fresa.webp");

        assertEquals(2L, producto.getId());
        assertEquals("Pastel de fresa", producto.getName());
        assertEquals(15990.0, producto.getPrice());
        assertEquals(15, producto.getStock());
        assertEquals(baseUrl + "pastel_fresa.webp", producto.getImagenUrl());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductoModel p1 = new ProductoModel(3L, "Torta de milhoja", 6500.0, 30, baseUrl + "torta_milhoja.webp");
        ProductoModel p2 = new ProductoModel(3L, "Torta de milhoja", 6500.0, 30, baseUrl + "torta_milhoja.webp");
        
        ProductoModel p3 = new ProductoModel(5L, "Torta Selva Negra", 2500.0, 50, baseUrl + "selva_negra.webp");

        assertEquals(p1, p2);        
        assertNotEquals(p1, p3);     
        assertNotEquals(p1, null);   
        
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        ProductoModel producto = new ProductoModel(4L, "Kuchen de manzana", 12500.0, 8, baseUrl + "kuchen_de_manzana.webp");
        String toString = producto.toString();

        assertTrue(toString.contains("Kuchen de manzana"));
        assertTrue(toString.contains("12500.0"));
        assertTrue(toString.contains("stock=8"));
        assertTrue(toString.contains("kuchen_de_manzana.webp"));
    }
}