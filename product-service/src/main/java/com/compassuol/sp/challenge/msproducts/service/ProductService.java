package com.compassuol.sp.challenge.msproducts.service;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(long productId);
    ProductResponseDto createProduct(ProductRequestDto productRequestDTO);
    ProductResponseDto updateProduct(Long productId , ProductRequestDto productRequestDTO);
    void deleteProductById(long id);
}
