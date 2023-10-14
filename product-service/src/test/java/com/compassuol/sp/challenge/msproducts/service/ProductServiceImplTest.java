package com.compassuol.sp.challenge.msproducts.service;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;

import com.compassuol.sp.challenge.msproducts.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import com.compassuol.sp.challenge.msproducts.repository.ProductRepository;
import com.compassuol.sp.challenge.msproducts.service.assembler.ProductDtoAssembler;
import com.compassuol.sp.challenge.msproducts.service.impl.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductDtoAssembler productDtoAssembler;

    @Mock
    private Product product;

    @Mock
    private ProductResponseDto productResponseDto;

    @Test
    public void createProduct_withValidData_ReturnsProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO.getName());
        product.setValue(PRODUCT_REQUEST_DTO.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO.getDescription());

        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productDtoAssembler.toModel(PRODUCT_REQUEST_DTO)).thenReturn(product);
        when(productDtoAssembler.toDto(any(Product.class))).thenReturn(PRODUCT_RESPONSE_DTO);

        ProductResponseDto responseDto = productService.createProduct(PRODUCT_REQUEST_DTO);

        assertThat(responseDto.getName()).isEqualTo(product.getName());
        assertThat(responseDto.getValue()).isEqualTo(product.getValue());
        assertThat(responseDto.getDescription()).isEqualTo(product.getDescription());
    }
    @Test
    public void createProduct_WithInvalidName_ThrowsException() {
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO_INVALID_NAME.getName());
        product.setValue(PRODUCT_REQUEST_DTO_INVALID_NAME.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO_INVALID_NAME.getDescription());

        when(productDtoAssembler.toModel(PRODUCT_REQUEST_DTO_INVALID_NAME)).thenReturn(product);

        try {
            productService.createProduct(PRODUCT_REQUEST_DTO_INVALID_NAME);
        } catch (InvalidDataException e) {
            assertThat("The name field cannot be empty.").isEqualTo(e.getMessage());
            assertThat("name").isEqualTo(e.getField());
        }
    }
    @Test
    public void createProduct_WithInvalidValue_ThrowsException() {
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO_INVALID_VALUE.getName());
        product.setValue(PRODUCT_REQUEST_DTO_INVALID_VALUE.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO_INVALID_VALUE.getDescription());

        when(productDtoAssembler.toModel(PRODUCT_REQUEST_DTO_INVALID_VALUE)).thenReturn(product);

        try {
            productService.createProduct(PRODUCT_REQUEST_DTO_INVALID_VALUE);
        } catch (InvalidDataException e) {
            assertThat("The value field must be greater than zero.").isEqualTo(e.getMessage());
            assertThat("value").isEqualTo(e.getField());
        }
    }
    @Test
    public void createProduct_WithInvalidDescription_ThrowsException() {
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO_INVALID_DESCRIPTION.getName());
        product.setValue(PRODUCT_REQUEST_DTO_INVALID_DESCRIPTION.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO_INVALID_DESCRIPTION.getDescription());

        when(productDtoAssembler.toModel(PRODUCT_REQUEST_DTO_INVALID_DESCRIPTION)).thenReturn(product);

        try {
            productService.createProduct(PRODUCT_REQUEST_DTO_INVALID_DESCRIPTION);
        } catch (InvalidDataException e) {
            assertThat("The description field must contain 10 or more characters.").isEqualTo(e.getMessage());
            assertThat("description").isEqualTo(e.getField());
        }
    }
    @Test
    public void getProductById_WithValidId_ReturnsProduct(){
        var productTest = PRODUCT;
        when(productRepository.findById(1L)).thenReturn(Optional.of(productTest));

        var systemUnderTest = productService.getProductById(1L);

        assertThat(productDtoAssembler.toDto(productTest)).isEqualTo(systemUnderTest);
    }

    @Test
    public void getProductById_WithInvalidId_Throwable() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        var systemUnderTest = assertThrows(EntityNotFoundException.class,
                () -> productService.getProductById(1L));

        assertThat(systemUnderTest.getClass()).isEqualTo(EntityNotFoundException.class);
    }

    @Test
    public void getAllProducts_WithValidRequest_ReturnsProducts(){
        Product product= new Product(1L,"product",10.0,"product description");
        var productTestToDto = productDtoAssembler.toDto(new Product(1L,"product",10.0,"product description"));
        when(productRepository.findAll()).thenReturn(List.of(product));

        assertThat(productTestToDto).isEqualTo(product);

    }

}


