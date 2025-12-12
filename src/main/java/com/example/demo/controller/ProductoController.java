package com.example.demo.controller; 

import com.example.demo.model.ProductoModel; 
import com.example.demo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Catálogo de Productos", description = "Operaciones del inventario de pastelería")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Listar todos los productos", description = "Devuelve la lista incluyendo los cargados automáticamente")
    @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductoModel>> getAllProducts() {
        return ResponseEntity.ok(productoService.getAllProducts()); 
    }

    @Operation(summary = "Crear un nuevo producto", description = "Guarda un producto manualmente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Creado"),
        @ApiResponse(responseCode = "400", description = "Error en datos")
    })
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductoModel producto) {
        try {
            // 1. Validar si viene sin imagen
            if (producto.getImagenUrl() == null || producto.getImagenUrl().trim().isEmpty()) {
                producto.setImagenUrl("https://placehold.co/400x300/e9ecef/6c757d?text=Sin+Imagen");
            }

            // 2. Guardar en base de datos
            ProductoModel savedProduct = productoService.saveProduct(producto);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el producto: " + e.getMessage());
        }
    }

    @Operation(summary = "Buscar por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> getProductById(@PathVariable Long id) {
        return productoService.getProductById(id)
                .map(ResponseEntity::ok) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    @Operation(summary = "Actualizar producto")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> updateProduct(@PathVariable Long id, @RequestBody ProductoModel productoDetails) {
        ProductoModel updated = productoService.updateProduct(id, productoDetails);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build(); 
    }

    @Operation(summary = "Eliminar producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productoService.deleteProduct(id);
        return ResponseEntity.noContent().build(); 
    }
}