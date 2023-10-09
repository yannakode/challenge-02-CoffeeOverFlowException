package com.compassuol.sp.challenge.msproducts.service.impl;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import com.compassuol.sp.challenge.msproducts.repository.ProductRepository;
import com.compassuol.sp.challenge.msproducts.service.ProductService;
import com.compassuol.sp.challenge.msproducts.service.assembler.ProductDtoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
    private final ProductDtoAssembler assembler;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> response = productRepository.findAll();
        ArrayList<ProductResponseDto> productsList = new ArrayList<>();

        response.forEach(product -> {
            productsList.add(assembler.toDto(product));
        });

        return productsList;
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
