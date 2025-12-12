package com.example.demo.testUnitarios;

import com.example.demo.model.ProductoModel;
import com.example.demo.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoRepositoryTest {

    private ProductoRepository productoRepository;
    private final String baseUrl = "http://localhost:8080/images/";

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
    }

    @Test
    void testSaveProduct() {
        ProductoModel producto = new ProductoModel(1L, "Pastel de Chocolate", 18500.0, 10, baseUrl + "pastel_chocolate.webp");
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(producto);

        ProductoModel saved = productoRepository.save(producto);

        assertNotNull(saved);
        assertEquals("Pastel de Chocolate", saved.getName());
        assertEquals(18500.0, saved.getPrice());
    }

    @Test
    void testFindById() {
        ProductoModel producto = new ProductoModel(2L, "Pastel de fresa", 15990.0, 15, baseUrl + "pastel_fresa.webp");
        when(productoRepository.findById(2L)).thenReturn(Optional.of(producto));

        Optional<ProductoModel> found = productoRepository.findById(2L);

        assertTrue(found.isPresent());
        assertEquals("Pastel de fresa", found.get().getName());
    }

    @Test
    void testFindAll() {
        List<ProductoModel> productos = Arrays.asList(
            new ProductoModel(3L, "Torta de milhoja", 6500.0, 30, baseUrl + "torta_milhoja.webp"),
            new ProductoModel(4L, "Kuchen de manzana", 12500.0, 8, baseUrl + "kuchen_de_manzana.webp")
        );
        when(productoRepository.findAll()).thenReturn(productos);

        List<ProductoModel> result = productoRepository.findAll();

        assertEquals(2, result.size());
        assertEquals("Torta de milhoja", result.get(0).getName());
        assertEquals("Kuchen de manzana", result.get(1).getName());
    }

    @Test
    void testExistsById() {
        when(productoRepository.existsById(5L)).thenReturn(true);

        boolean exists = productoRepository.existsById(5L);

        assertTrue(exists);
        verify(productoRepository, times(1)).existsById(5L);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productoRepository).deleteById(1L);

        productoRepository.deleteById(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}
