package com.compassuol.sp.challenge.msproducts.service.assembler;


import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDtoAssembler {
    private final ModelMapper modelMapper;

    public ProductResponseDto toDto(Product product){
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public Product toModel(ProductRequestDto productRequestDTO){
        return modelMapper.map(productRequestDTO, Product.class);
    }
}
