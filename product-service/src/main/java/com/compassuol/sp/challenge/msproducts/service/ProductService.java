package com.compassuol.sp.challenge.msproducts.service;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(long productId);
    ProductResponseDto createProduct(ProductRequestDto productRequestDTO);
    ProductResponseDto updateProduct(ProductRequestDto productRequestDTO);
    boolean deleteProduct();
}
