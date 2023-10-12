package com.compassuol.sp.challenge.msproducts.controller;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;

import com.compassuol.sp.challenge.msproducts.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductServiceImpl productService;

    @Test
    public void createProduct_WithValidData_ReturnsCreated() throws Exception {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName(PRODUCT_REQUEST_DTO.getName());
        productResponseDto.setValue(PRODUCT_REQUEST_DTO.getValue());
        productResponseDto.setDescription(PRODUCT_REQUEST_DTO.getDescription());

        when(productService.createProduct(PRODUCT_REQUEST_DTO)).thenReturn(productResponseDto);


        mockMvc.perform(post("/products").content(objectMapper.writeValueAsString(PRODUCT_REQUEST_DTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
