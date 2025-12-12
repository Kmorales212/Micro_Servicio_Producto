package com.example.demo.testUnitarios;

import com.example.demo.controller.ProductoController;
import com.example.demo.model.ProductoModel;
import com.example.demo.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductoControllerTest {

    private MockMvc mockMvc;
    private ProductoService productoService;
    private ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/images/";

    @BeforeEach
    void setUp() {
        productoService = mock(ProductoService.class);
        objectMapper = new ObjectMapper();
        
        ProductoController productoController = new ProductoController(productoService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    void testGetAllProducts() throws Exception {
        ProductoModel p1 = new ProductoModel(1L, "Pastel de Chocolate", 18500.0, 10, baseUrl + "pastel_chocolate.webp");
        when(productoService.getAllProducts()).thenReturn(Arrays.asList(p1));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Pastel de Chocolate"));
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        ProductoModel input = new ProductoModel(null, "Pastel de fresa", 15990.0, 15, baseUrl + "pastel_fresa.webp");
        ProductoModel saved = new ProductoModel(1L, "Pastel de fresa", 15990.0, 15, baseUrl + "pastel_fresa.webp");
        
        when(productoService.saveProduct(any(ProductoModel.class))).thenReturn(saved);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pastel de fresa"));
    }

    @Test
    void testCreateProduct_DefaultImage() throws Exception {
        ProductoModel input = new ProductoModel(null, "Torta de milhoja", 6500.0, 30, "");
        
        when(productoService.saveProduct(any(ProductoModel.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.imagenUrl").value(containsString("placehold.co")));
    }

    @Test
    void testCreateProduct_Exception() throws Exception {
        when(productoService.saveProduct(any(ProductoModel.class))).thenThrow(new RuntimeException("Error fatal"));

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ProductoModel())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Error al crear el producto")));
    }

    @Test
    void testGetProductById_Success() throws Exception {
        ProductoModel p = new ProductoModel(4L, "Kuchen de manzana", 12500.0, 8, baseUrl + "kuchen_de_manzana.webp");
        when(productoService.getProductById(4L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/api/productos/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kuchen de manzana"));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productoService.getProductById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        ProductoModel p = new ProductoModel(5L, "Torta Selva Negra", 2500.0, 50, baseUrl + "selva_negra.webp");
        when(productoService.updateProduct(eq(5L), any(ProductoModel.class))).thenReturn(p);

        mockMvc.perform(put("/api/productos/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Torta Selva Negra"));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        when(productoService.updateProduct(anyLong(), any(ProductoModel.class))).thenReturn(null);

        mockMvc.perform(put("/api/productos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ProductoModel())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productoService).deleteProduct(1L);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());

        verify(productoService, times(1)).deleteProduct(1L);
    }
}