package com.example.testeo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testeo.models.CategoryModel;
import com.example.testeo.repositories.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categorieRepository;

public  CategoryModel saveCategory (CategoryModel category){
    return categorieRepository.save(category);
}

public Optional<CategoryModel> findById(Long id) {
    return categorieRepository.findById(id);
 }

}
