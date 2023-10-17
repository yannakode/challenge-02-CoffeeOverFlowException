package com.compassuol.sp.challenge.msproducts.service.assembler;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductDtoAssemblerTest {
    @InjectMocks
    ProductDtoAssembler productDtoAssembler;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void testToDto() {
        when(modelMapper.map(PRODUCT, ProductResponseDto.class)).thenReturn(PRODUCT_RESPONSE_DTO);
        ProductResponseDto sut = productDtoAssembler.toDto(PRODUCT);

        assertNotNull(sut);
        assertEquals(PRODUCT_RESPONSE_DTO, sut);
    }
    @Test
    public void testToModel() {
        when(modelMapper.map(PRODUCT_REQUEST_DTO, Product.class)).thenReturn(PRODUCT);

        Product sut = productDtoAssembler.toModel(PRODUCT_REQUEST_DTO);

        assertNotNull(sut);
        assertEquals(PRODUCT, sut);
    }

}
