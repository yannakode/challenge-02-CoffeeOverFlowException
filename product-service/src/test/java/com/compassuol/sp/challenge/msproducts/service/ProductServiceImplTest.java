package com.compassuol.sp.challenge.msproducts.service;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;

import com.compassuol.sp.challenge.msproducts.exceptions.GeneralExceptionHandler;
import com.compassuol.sp.challenge.msproducts.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msproducts.model.dto.ProductRequestDto;
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
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
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

    @Test
    public void createProduct_withValidData_ReturnsProduct() {

        ProductRequestDto productRequestDto= new ProductRequestDto();
        productRequestDto.setValue(10.0);
        productRequestDto.setDescription("Product Description");
        productRequestDto.setName("Product name");

        Product product = new Product();
        product.setId(1L);
        product.setName(productRequestDto.getName());
        product.setValue(productRequestDto.getValue());
        product.setDescription(productRequestDto.getDescription());

        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productDtoAssembler.toModel(productRequestDto)).thenReturn(product);
        when(productDtoAssembler.toDto(any(Product.class))).thenReturn(PRODUCT_RESPONSE_DTO);

        ProductResponseDto responseDto = productService.createProduct(productRequestDto);

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
    public void createProduct_WithDuplicatedName_ThrowsException() {
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO.getName());
        product.setValue(PRODUCT_REQUEST_DTO.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO.getDescription());

        when(productRepository.findByName(PRODUCT.getName())).thenReturn(Optional.of(product));
        when(productDtoAssembler.toModel(PRODUCT_REQUEST_DTO)).thenReturn(product);

        try {
            productService.createProduct(PRODUCT_REQUEST_DTO);
        } catch (DataIntegrityViolationException e) {
            assertThat("Product name already exists").isEqualTo(e.getMessage());
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
    public void getAllProducts_WithValidRequest_ReturnsProducts(){
        when(productRepository.findAll()).thenReturn(List.of(PRODUCT));
        when(productDtoAssembler.toDto(PRODUCT)).thenReturn(PRODUCT_RESPONSE_DTO);

        List<ProductResponseDto> sut = productService.getAllProducts();

        assertThat(sut.get(0).getId()).isEqualTo(PRODUCT.getId());
        assertThat(sut.get(0).getName()).isEqualTo(PRODUCT.getName());
        assertThat(sut.get(0).getValue()).isEqualTo(PRODUCT.getValue());
        assertThat(sut.get(0).getDescription()).isEqualTo(PRODUCT.getDescription());
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
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        var systemUnderTest = assertThrows(EntityNotFoundException.class,
                () -> productService
                        .getProductById(1L));

        assertThat(systemUnderTest
                .getClass())
                .isEqualTo(EntityNotFoundException.class);
    }

    @Test
    public void updateProduct_withValidData_ReturnsProduct() {

        ProductRequestDto productRequestDto= new ProductRequestDto();
        productRequestDto.setValue(10.0);
        productRequestDto.setDescription("Product Description");
        productRequestDto.setName("Product name");

        Product product = new Product();
        product.setId(1L);
        product.setName(productRequestDto.getName());
        product.setValue(productRequestDto.getValue());
        product.setDescription(productRequestDto.getDescription());

        productRequestDto.setName("New Product Name");

        Product productUpdated = new Product();
        productUpdated.setId(1L);
        productUpdated.setName(productRequestDto.getName());
        productUpdated.setValue(productRequestDto.getValue());
        productUpdated.setDescription(productRequestDto.getDescription());

        ProductResponseDto productResponseDto = new ProductResponseDto(1L, "New Product Name", 10.0, "Product Description");

        when(productRepository.save(any(Product.class))).thenReturn(productUpdated);
        when(productDtoAssembler.toDto(any(Product.class))).thenReturn(productResponseDto);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDto sut = productService.updateProduct(1L, productRequestDto);

        assertThat(sut.getName()).isEqualTo(productUpdated.getName());
        assertThat(sut.getValue()).isEqualTo(productUpdated.getValue());
        assertThat(sut.getDescription()).isEqualTo(productUpdated.getDescription());
    }
    @Test
    public void updateProduct_WithInvalidName_ThrowsException() {
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO_INVALID_NAME.getName());
        product.setValue(PRODUCT_REQUEST_DTO_INVALID_NAME.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO_INVALID_NAME.getDescription());

        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));

        try {
            productService.updateProduct(1L, PRODUCT_REQUEST_DTO_INVALID_NAME);
        } catch (InvalidDataException e) {
            assertThat("The name field cannot be empty.").isEqualTo(e.getMessage());
            assertThat("name").isEqualTo(e.getField());
        }
    }

    @Test
    public void updateProduct_WithDuplicatedName_ThrowsException() {
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO.getName());
        product.setValue(PRODUCT_REQUEST_DTO.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO.getDescription());

        when(productRepository.findByName(PRODUCT.getName())).thenReturn(Optional.of(product));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        try {
            productService.updateProduct(1L, PRODUCT_REQUEST_DTO);
        } catch (DataIntegrityViolationException e) {
            assertThat("Product name already exists").isEqualTo(e.getMessage());
        }
    }
    @Test
    public void updateProduct_WithInvalidValue_ThrowsException() {
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_REQUEST_DTO_INVALID_VALUE.getName());
        product.setValue(PRODUCT_REQUEST_DTO_INVALID_VALUE.getValue());
        product.setDescription(PRODUCT_REQUEST_DTO_INVALID_VALUE.getDescription());

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        try {
            productService.updateProduct(1L, PRODUCT_REQUEST_DTO_INVALID_VALUE);
        } catch (InvalidDataException e) {
            assertThat("The value field needs to be greater than zero.").isEqualTo(e.getMessage());
            assertThat("value").isEqualTo(e.getField());
        }
    }
   @Test
   public void deleteProductById_WithValidId_DoesNotException(){
        productService.deleteProductById(1L);

        verify(productRepository).deleteById(1L);
    }

}

