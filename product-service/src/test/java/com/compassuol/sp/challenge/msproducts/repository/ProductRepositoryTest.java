package com.compassuol.sp.challenge.msproducts.repository;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;

import com.compassuol.sp.challenge.msproducts.model.dto.ProductResponseDto;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.compassuol.sp.challenge.msproducts.service.assembler.ProductDtoAssembler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createProduct_WithValidData_ReturnsProduct() {
        Product product = productRepository.save(PRODUCT);
        Product sut = testEntityManager.find(Product.class, product.getId());

        assertThat(sut.getName()).isEqualTo(product.getName());
        assertThat(sut.getValue()).isEqualTo(product.getValue());
        assertThat(sut.getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void createProduct_WithInvalidData_ThrowsException() {
        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_NAME));
        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_VALUE));
        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_DESCRIPTION));
    }
}
