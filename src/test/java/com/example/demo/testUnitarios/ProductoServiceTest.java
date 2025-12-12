package com.example.demo.testUnitarios;

import com.example.demo.model.ProductoModel;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    private ProductoRepository productoRepository;
    private ProductoService productoService;
    private final String baseUrl = "http://localhost:8080/images/";

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        productoService = new ProductoService(productoRepository);
    }

    @Test
    void testSaveProduct() {
        ProductoModel input = new ProductoModel(null, "Pastel de Chocolate", 18500.0, 10, baseUrl + "pastel_chocolate.webp");
        ProductoModel saved = new ProductoModel(1L, "Pastel de Chocolate", 18500.0, 10, baseUrl + "pastel_chocolate.webp");
        
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(saved);

        ProductoModel result = productoService.saveProduct(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pastel de Chocolate", result.getName());
        verify(productoRepository, times(1)).save(input);
    }

    @Test
    void testGetAllProducts() {
        List<ProductoModel> lista = Arrays.asList(
            new ProductoModel(2L, "Pastel de fresa", 15990.0, 15, baseUrl + "pastel_fresa.webp"),
            new ProductoModel(3L, "Torta de milhoja", 6500.0, 30, baseUrl + "torta_milhoja.webp")
        );

        when(productoRepository.findAll()).thenReturn(lista);

        List<ProductoModel> result = productoService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Pastel de fresa", result.get(0).getName());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        ProductoModel kuchen = new ProductoModel(4L, "Kuchen de manzana", 12500.0, 8, baseUrl + "kuchen_de_manzana.webp");
        when(productoRepository.findById(4L)).thenReturn(Optional.of(kuchen));

        Optional<ProductoModel> result = productoService.getProductById(4L);

        assertTrue(result.isPresent());
        assertEquals("Kuchen de manzana", result.get().getName());
        verify(productoRepository, times(1)).findById(4L);
    }

    @Test
    void testUpdateProduct_Success() {
        Long id = 5L;
        ProductoModel existing = new ProductoModel(id, "Torta Selva Negra", 2500.0, 50, baseUrl + "selva_negra.webp");
        
        when(productoRepository.existsById(id)).thenReturn(true);
        when(productoRepository.save(any(ProductoModel.class))).thenReturn(existing);

        ProductoModel result = productoService.updateProduct(id, existing);

        assertNotNull(result);
        assertEquals("Torta Selva Negra", result.getName());
        verify(productoRepository, times(1)).save(existing);
    }

    @Test
    void testUpdateProduct_NotFound() {
        Long id = 99L;
        when(productoRepository.existsById(id)).thenReturn(false);

        ProductoModel result = productoService.updateProduct(id, new ProductoModel());

        assertNull(result);
        verify(productoRepository, never()).save(any(ProductoModel.class));
    }

    @Test
    void testDeleteProduct() {
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        productoService.deleteProduct(id);

        verify(productoRepository, times(1)).deleteById(id);
    }
}