package com.compassuol.sp.challenge.msproducts.service.impl;

import com.compassuol.sp.challenge.msproducts.exceptions.customExceptions.InvalidDataException;
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

        if (product.getDescription().length() < 10) throw new InvalidDataException("The description field must contain 10 or more characters.", "description");
        if(product.getName() == null || product.getName().isEmpty()) throw new InvalidDataException("The name field cannot be empty.", "name");
        if(product.getValue() <= 0) throw new InvalidDataException("The value field must be greater than zero.", "value");

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
