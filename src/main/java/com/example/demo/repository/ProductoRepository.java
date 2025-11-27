package com.example.demo.repository; 

import com.example.demo.model.ProductoModel; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long>{}