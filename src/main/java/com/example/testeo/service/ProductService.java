package com.example.testeo.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.testeo.models.ProductDto;
import com.example.testeo.models.ProductModel;
import com.example.testeo.repositories.ProductsRepository;


@Service
public class ProductService {

    @Autowired
    private ProductsRepository productRepository;

public Page<ProductModel> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
} 

public Optional<ProductModel> getProductById(Long id) {
    return productRepository.findById(id);
}

@Async
public CompletableFuture<Void> createProducts(List<ProductModel> products) {
    productRepository.saveAll(products);
    return CompletableFuture.completedFuture(null);
}

public Page<ProductDto> convertProductPage(Page<ProductModel> productPage) {
    List<ProductDto> productDTOs = productPage.getContent().stream()
            .map(product -> convertProductDto(product))
            .collect(Collectors.toList());

    return new PageImpl<>(productDTOs, productPage.getPageable(), productPage.getTotalElements());
}

private ProductDto convertProductDto(ProductModel product) {
    ProductDto dto = new ProductDto();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setCategoryName(product.getCategory().getName());
    dto.setQuantityPerUnit(product.getQuantityPerUnit());
    dto.setUnitPrice(product.getUnitPrice());
    dto.setUnitsInStock(product.getUnitsInStock());
    dto.setUnitsOnOrder(product.getUnitsOnOrder());
    dto.setReorderLevel(product.getReorderLevel());
    dto.setDiscontinued(product.getDiscontinued());
    return dto;
}



}