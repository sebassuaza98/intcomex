package com.example.testeo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testeo.models.CategoryModel;
import com.example.testeo.models.ProductDto;
import com.example.testeo.models.ProductModel;
import com.example.testeo.service.CategoryService;
import com.example.testeo.service.ProductService;
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/listPage")
    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
        Page<ProductModel> productPage = productService.getAllProducts(pageable);
        Page<ProductDto> productDTOPage = productService.convertProductPage(productPage);
        return new ResponseEntity<>(productDTOPage, HttpStatus.OK);
    }

    @GetMapping("/list/picture/{id}")
    public ResponseEntity<Object> getProductCategoryPicture(@PathVariable("id") Long id) {
        Optional<ProductModel> product = productService.getProductById(id);
        if (product.isPresent()) {
            CategoryModel category = product.get().getCategory();
            if (category != null) {
                byte[] image = category.getPicture();
                if (image != null) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    headers.setContentLength(image.length);
    
                    return new ResponseEntity<>(image, headers, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody String productRequest) {
        try {
            List<ProductModel> productsBatch1 = new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                ProductModel product = productService.generateRandomProduct(productRequest);
                createProductWithCategoryId(product, 2L);
                productsBatch1.add(product);
            }

            List<ProductModel> productsBatch2 = new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                ProductModel product = productService.generateRandomProduct(productRequest);
                createProductWithCategoryId(product, 1L);
                productsBatch2.add(product);
            }

            CompletableFuture<Void> future1 = productService.createProducts(productsBatch1);
            CompletableFuture<Void> future2 = productService.createProducts(productsBatch2);
            CompletableFuture.allOf(future1, future2).join();

            return ResponseEntity.status(HttpStatus.CREATED).body("Products created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    public void createProductWithCategoryId(ProductModel product, Long categoryId) {
        Optional<CategoryModel> category = categoryService.findById(categoryId);
        if (category.isPresent()) {
            product.setCategory(category.get());
        }
    }


}
