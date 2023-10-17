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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoAssembler assembler;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> response = productRepository.findAll();
        List<ProductResponseDto> productsList = new ArrayList<>();

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

        Optional<Product> getNameResponse = productRepository.findByName(product.getName());
        if (getNameResponse.isPresent()) throw new DataIntegrityViolationException("Product name already exists");

        if (product.getDescription().length() < 10) throw new InvalidDataException("The description field must contain 10 or more characters.", "description");
        if(product.getName() == null || product.getName().isEmpty()) throw new InvalidDataException("The name field cannot be empty.", "name");
        if(product.getValue() <= 0) throw new InvalidDataException("The value field must be greater than zero.", "value");

        var newProduct = productRepository.save(product);
        return assembler.toDto(newProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDTO) {
        Optional<Product> productOp = productRepository.findById(productId);

        if(productOp.isEmpty()) throw new InvalidDataException("Product Id not found", "Id");

        if(productRequestDTO.getName() == null || productRequestDTO.getName().length() < 1) throw new InvalidDataException("The name field cannot be empty.", "name");

        if(productRequestDTO.getValue() <= 0) throw new InvalidDataException("The value field needs to be greater than zero.", "value");

        if(productRequestDTO.getDescription().length() < 10) throw new InvalidDataException("The description field needs to be greater than 10.", "Description");

        if(productRepository.findByName(productRequestDTO.getName()).isPresent()) throw new DataIntegrityViolationException("Product name already exists");

        Product originalProduct = productOp.get();
        originalProduct.setValue(productRequestDTO.getValue());
        originalProduct.setName(productRequestDTO.getName());
        originalProduct.setDescription(productRequestDTO.getDescription());
        productRepository.save(originalProduct);

        return assembler.toDto(originalProduct);
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.findById(id);
        productRepository.deleteById(id);
    }

}
