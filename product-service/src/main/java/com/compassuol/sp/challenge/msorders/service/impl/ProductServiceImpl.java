package com.compassuol.sp.challenge.msorders.service.impl;

import com.compassuol.sp.challenge.msorders.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msorders.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msorders.repository.ProductRepository;
import com.compassuol.sp.challenge.msorders.service.ProductService;
import com.compassuol.sp.challenge.msorders.service.assembler.ProductDtoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoAssembler assembler;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return null;
    }

    @Override
    public ProductResponseDto getProductById(long productId) {
        return null;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDTO) {
        var product = assembler.toModel(productRequestDTO);
        var newProduct = productRepository.save(product);
        return assembler.toDto(newProduct);
    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDTO) {
        return null;
    }

    @Override
    public boolean deleteProduct() {
        return false;
    }
}
