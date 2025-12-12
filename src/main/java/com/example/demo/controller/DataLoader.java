package com.example.demo.controller;

import com.example.demo.model.ProductoModel;
import com.example.demo.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository repository;

    public DataLoader(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            System.out.println("--- CARGANDO IMÁGENES LOCALES ---");
            
            String baseUrl = "http://localhost:8085/images/";

            List<ProductoModel> lista = List.of(
                new ProductoModel(null, "Pastel de Chocolate", 18500.0, 10, baseUrl + "pastel_chocolate.webp"),
                new ProductoModel(null, "Pastel de fresa", 15990.0, 15, baseUrl + "pastel_fresa.webp"),
                new ProductoModel(null, "Torta de milhoja", 6500.0, 30, baseUrl + "torta_milhoja.webp"),
                new ProductoModel(null, "Kuchen de manzana", 12500.0, 8, baseUrl + "kuchen_de_manzana.webp"),
                new ProductoModel(null, "Torta Selva Negra", 2500.0, 50, baseUrl +  "selva_negra.webp")
            );

            repository.saveAll(lista);
            System.out.println("--- ¡PRODUCTOS LOCALES GUARDADOS! ---");
        }
    }
}
 



