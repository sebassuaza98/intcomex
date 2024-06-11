package com.example.testeo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
//Toma los atributos deseados del ProductModel
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

public String processRandom(String input) {
        Pattern pattern = Pattern.compile("\\{\\{random(Int|Decimal|Boolean)\\s(.*?)\\}\\}");
        Matcher matcher = pattern.matcher(input);
        StringBuffer output = new StringBuffer();

        // Generan valores aleatorios 
        Random random = new Random();
        while (matcher.find()) {
            String expressionType = matcher.group(1);
            String[] params = matcher.group(2).split("\\s");

            switch (expressionType) {
                case "Int":
                    int min = Integer.parseInt(params[0]);
                    int max = Integer.parseInt(params[1]);
                    int randomInt = random.nextInt(max - min + 1) + min;
                    matcher.appendReplacement(output, String.valueOf(randomInt));
                    break;
                case "Decimal":
                    double minDouble = Double.parseDouble(params[0]);
                    double maxDouble = Double.parseDouble(params[1]);
                    double randomDouble = minDouble + (maxDouble - minDouble) * random.nextDouble();
                    matcher.appendReplacement(output, String.format("%.2f", randomDouble));
                    break;
                case "Boolean":
                    boolean randomBoolean = random.nextBoolean();
                    matcher.appendReplacement(output, String.valueOf(randomBoolean));
                    break;
            }
        }
        matcher.appendTail(output);
        return output.toString();

    }

    public ProductModel generateRandomProduct(String productRequest) {
        String productName = extractRandomValue(productRequest, "name");

        productName = processRandom(productName);

        Random rand = new Random();
        String quantityPerUnit = rand.nextInt(20) + 1 + " unidades";
        BigDecimal unitPrice = BigDecimal.valueOf(rand.nextInt(91) + 10); // Entre 10 y 100
        Integer unitsInStock = rand.nextInt(500) + 1;
        Integer unitsOnOrder = rand.nextInt(100) + 1;
        Integer reorderLevel = rand.nextInt(50) + 1;
        Boolean discontinued = rand.nextBoolean();

        ProductModel product = new ProductModel();
        product.setName(productName);
        product.setQuantityPerUnit(quantityPerUnit);
        product.setUnitPrice(unitPrice);
        product.setUnitsInStock(unitsInStock);
        product.setUnitsOnOrder(unitsOnOrder);
        product.setReorderLevel(reorderLevel);
        product.setDiscontinued(discontinued);

        return product;
    }

    public String extractRandomValue(String productRequest, String key) {
        String pattern = "\"" + key + "\":\\s*(\"[^\"]+\"|\\d+\\.?\\d*(?:[eE][-+]?\\d+)?|true|false)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(productRequest);
        matcher.find();
        return matcher.group(1);
    }

}