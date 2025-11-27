package com.example.demo.controller; 

import com.example.demo.model.ProductoModel; 
import com.example.demo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
// 1. Título y descripción en Swagger
@Tag(name = "Catálogo de Productos", description = "Operaciones para crear, listar, editar y eliminar productos del inventario")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // --- CREAR PRODUCTO ---
    @Operation(summary = "Crear un nuevo producto", description = "Guarda un producto en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductoModel> createProduct(@RequestBody ProductoModel producto) {
        ProductoModel savedProduct = productoService.saveProduct(producto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); 
    }

    // --- LISTAR PRODUCTOS ---
    @Operation(summary = "Listar todos los productos", description = "Devuelve el catálogo completo de productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductoModel>> getAllProducts() {
        List<ProductoModel> productos = productoService.getAllProducts();
        return ResponseEntity.ok(productos); 
    }

    // --- BUSCAR POR ID ---
    @Operation(summary = "Buscar producto por ID", description = "Obtiene los detalles de un producto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> getProductById(
            @Parameter(description = "ID del producto a buscar") @PathVariable Long id) {
        return productoService.getProductById(id)
                .map(ResponseEntity::ok) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    // --- ACTUALIZAR PRODUCTO ---
    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> updateProduct(
            @Parameter(description = "ID del producto a editar") @PathVariable Long id, 
            @RequestBody ProductoModel productoDetails) {
        ProductoModel updatedProduct = productoService.updateProduct(id, productoDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct); 
        }
        return ResponseEntity.notFound().build(); 
    }

    // --- ELIMINAR PRODUCTO ---
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del catálogo permanentemente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "El producto no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto a eliminar") @PathVariable Long id) {
        productoService.deleteProduct(id);
        return ResponseEntity.noContent().build(); 
    }
}