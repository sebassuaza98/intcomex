package com.example.testeo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.testeo.models.CategoryModel;
import com.example.testeo.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(
            @RequestParam("name") String name,
            @RequestParam("picture") MultipartFile picture) {
        try {
            if (name == null || name.isEmpty()) {
                return new ResponseEntity<>("Name is required", HttpStatus.BAD_REQUEST);
            }
            if (picture == null || picture.isEmpty()) {
                return new ResponseEntity<>("Picture is required", HttpStatus.BAD_REQUEST);
            }
            CategoryModel category = new CategoryModel();
            category.setName(name);
            category.setPicture(picture.getBytes());
            categoryService.saveCategory(category);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload picture", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
