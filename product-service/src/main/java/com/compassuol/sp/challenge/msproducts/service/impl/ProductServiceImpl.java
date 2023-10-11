package com.compassuol.sp.challenge.msproducts.service.impl;

import com.compassuol.sp.challenge.msproducts.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import com.compassuol.sp.challenge.msproducts.repository.ProductRepository;
import com.compassuol.sp.challenge.msproducts.service.ProductService;
import com.compassuol.sp.challenge.msproducts.service.assembler.ProductDtoAssembler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
        var product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        return assembler.toDto(product);
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDTO) {
        var product = assembler.toModel(productRequestDTO);

        if (product.getDescription().length() < 10) throw new InvalidDataException("O campo description deve conter 10 ou mais caracteres.", "description");
        if(product.getName() == null || product.getName().length() < 1) throw new InvalidDataException("O campo name não pode estar vazio.", "name");
        if(product.getValue() <= 0) throw new InvalidDataException("O campo value precisa ser maior que zero.", "value");

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
