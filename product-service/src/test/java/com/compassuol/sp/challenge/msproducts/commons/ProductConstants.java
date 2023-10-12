package com.compassuol.sp.challenge.msproducts.commons;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;

public class ProductConstants {
    public static final ProductRequestDto PRODUCT_REQUEST_DTO = new ProductRequestDto("Product name", 10.0, "Product Description");
    public static final ProductResponseDto PRODUCT_RESPONSE_DTO = new ProductResponseDto(1L, "Product name", 10.0, "Product Description");
    public static final ProductRequestDto PRODUCT_REQUEST_DTO_INVALID_NAME = new ProductRequestDto("", 10.0, "Product Description");
    public static final ProductRequestDto PRODUCT_REQUEST_DTO_INVALID_VALUE = new ProductRequestDto("Product name", -1D, "Product Description");
    public static final ProductRequestDto PRODUCT_REQUEST_DTO_INVALID_DESCRIPTION = new ProductRequestDto("Product name", -1D, "Product1");

}
