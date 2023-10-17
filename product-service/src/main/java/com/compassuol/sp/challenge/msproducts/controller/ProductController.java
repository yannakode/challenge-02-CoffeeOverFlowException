package com.compassuol.sp.challenge.msproducts.controller;

import com.compassuol.sp.challenge.msproducts.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto productRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequestDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto>  updateProduct(@PathVariable(value = "id") Long id, @RequestBody @Valid ProductRequestDto productRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, productRequestDto));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        var productResponse = productService.getProductById(productId);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long productId){
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

}
