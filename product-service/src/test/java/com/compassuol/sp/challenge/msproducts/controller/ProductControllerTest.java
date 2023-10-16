package com.compassuol.sp.challenge.msproducts.controller;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;


import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;
    @InjectMocks
    private ProductController productController;

    @Test
    public void createProduct_WithValidData_ReturnsCreated() throws Exception {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName(PRODUCT_REQUEST_DTO.getName());
        productResponseDto.setValue(PRODUCT_REQUEST_DTO.getValue());
        productResponseDto.setDescription(PRODUCT_REQUEST_DTO.getDescription());

        when(productService.createProduct(PRODUCT_REQUEST_DTO)).thenReturn(productResponseDto);

        ResponseEntity<ProductResponseDto> sut = productController.createProduct(PRODUCT_REQUEST_DTO);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody()).isEqualTo(productResponseDto);
    }
    @Test
    void updateProduct_withValidData_returnsOk() throws Exception {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName(PRODUCT_REQUEST_DTO.getName());
        productResponseDto.setValue(PRODUCT_REQUEST_DTO.getValue());
        productResponseDto.setDescription(PRODUCT_REQUEST_DTO.getDescription());

        when(productService.updateProduct(productResponseDto.getId() ,PRODUCT_REQUEST_DTO)).thenReturn(productResponseDto);

        ResponseEntity<ProductResponseDto> sut = productController.updateProduct(productResponseDto.getId(), PRODUCT_REQUEST_DTO);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(productResponseDto);
    }
}
