package com.example.demo.controller; 

import com.example.demo.model.ProductoModel; 
import com.example.demo.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos") 
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<ProductoModel> createProduct(@RequestBody ProductoModel producto) {
        ProductoModel savedProduct = productoService.saveProduct(producto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<List<ProductoModel>> getAllProducts() {
        List<ProductoModel> productos = productoService.getAllProducts();
        return ResponseEntity.ok(productos); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> getProductById(@PathVariable Long id) {
        return productoService.getProductById(id)
                .map(ResponseEntity::ok) 
                .orElse(ResponseEntity.notFound().build()); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> updateProduct(@PathVariable Long id, @RequestBody ProductoModel productoDetails) {
        ProductoModel updatedProduct = productoService.updateProduct(id, productoDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct); 
        }
        return ResponseEntity.notFound().build(); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productoService.deleteProduct(id);
        return ResponseEntity.noContent().build(); 
    }
}