package com.compassuol.sp.challenge.msproducts.repository;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.compassuol.sp.challenge.msproducts.service.assembler.ProductDtoAssembler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @Mock
    private ProductRepository productRepository;

    @Test
    public void createProduct_WithValidData_ReturnsProduct() {
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);

        Product sut = productRepository.save(PRODUCT);

        assertThat(sut).isEqualTo(PRODUCT);
    }

    @Test
    public void createProduct_WithInvalidData_ThrowsException() {
        when(productRepository.save(PRODUCT_INVALID_NAME)).thenThrow(RuntimeException.class);
        when(productRepository.save(PRODUCT_INVALID_VALUE)).thenThrow(RuntimeException.class);
        when(productRepository.save(PRODUCT_INVALID_DESCRIPTION)).thenThrow(RuntimeException.class);


        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_NAME));
        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_VALUE));
        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_DESCRIPTION));
    }
}
