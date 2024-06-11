package com.example.testeo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.testeo.models.CategoryModel;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryModel, Long>{
    
}
