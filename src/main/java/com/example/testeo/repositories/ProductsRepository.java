package com.example.testeo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testeo.models.ProductModel;

@Repository
public interface ProductsRepository extends JpaRepository<ProductModel, Long>{
    
}
