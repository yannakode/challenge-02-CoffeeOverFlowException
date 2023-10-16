package com.compassuol.sp.challenge.msproducts.repository;

import static com.compassuol.sp.challenge.msproducts.commons.ProductConstants.*;
import com.compassuol.sp.challenge.msproducts.model.entity.Product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    public void updateProduct_WithValidData_ReturnsUpdatedProduct() {
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);

        Product sut = productRepository.save(PRODUCT);

        assertThat(sut).isEqualTo(PRODUCT);

        Product updatedProduct = new Product();
        updatedProduct.setId(sut.getId());
        updatedProduct.setName("Product name");
        updatedProduct.setValue(100.0);
        updatedProduct.setDescription("Description");

        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product updatedSut = productRepository.save(updatedProduct);

        assertThat(updatedSut).isEqualTo(updatedProduct);
    }
    @Test
    public void updateProduct_WithInvalidData_ThrowsException() {
        when(productRepository.save(PRODUCT_INVALID_NAME)).thenThrow(RuntimeException.class);
        when(productRepository.save(PRODUCT_INVALID_VALUE)).thenThrow(RuntimeException.class);
        when(productRepository.save(PRODUCT_INVALID_DESCRIPTION)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_NAME)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_VALUE)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> productRepository.save(PRODUCT_INVALID_DESCRIPTION)).isInstanceOf(RuntimeException.class);
    }
}
