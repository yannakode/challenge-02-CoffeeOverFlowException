package com.compassuol.sp.challenge.msproducts.commons;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;

public class ProductConstants {
    public static final Product PRODUCT= new Product(1L, "Product name", 10.0, "Product Description");
    public static final Product PRODUCT_INVALID_NAME = new Product("", 10.0, "Product Description");
    public static final Product PRODUCT_INVALID_VALUE = new Product("Product name", -1.0, "Product Description");
    public static final Product PRODUCT_INVALID_DESCRIPTION = new Product("Product name", 10.0, "Product1");



    public static final ProductRequestDto PRODUCT_REQUEST_DTO = new ProductRequestDto("Product name", 10.0, "Product Description");
    public static final ProductResponseDto PRODUCT_RESPONSE_DTO = new ProductResponseDto(1L, "Product name", 10.0, "Product Description");
    public static final ProductRequestDto PRODUCT_REQUEST_DTO_INVALID_NAME = new ProductRequestDto("", 10.0, "Product Description");
    public static final ProductRequestDto PRODUCT_REQUEST_DTO_INVALID_VALUE = new ProductRequestDto("Product name", -1D, "Product Description");
    public static final ProductRequestDto PRODUCT_REQUEST_DTO_INVALID_DESCRIPTION = new ProductRequestDto("Product name", -10.0, "Product1");
}
