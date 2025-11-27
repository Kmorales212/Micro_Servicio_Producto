package com.example.demo.service; 

import com.example.demo.model.ProductoModel; 
import com.example.demo.repository.ProductoRepository; 
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

   
    public ProductoModel saveProduct(ProductoModel producto) {
        return productoRepository.save(producto);
    }

    
    public List<ProductoModel> getAllProducts() {
        return productoRepository.findAll();
    }

    
    public Optional<ProductoModel> getProductById(Long id) {
        return productoRepository.findById(id);
    }

    
    public ProductoModel updateProduct(Long id, ProductoModel productoDetails) {
        if (productoRepository.existsById(id)) {
            productoDetails.setId(id); 
            return productoRepository.save(productoDetails);
        }
        return null; 
    }

    
    public void deleteProduct(Long id) {
        productoRepository.deleteById(id);
    }
}