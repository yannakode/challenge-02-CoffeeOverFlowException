package com.compassuol.sp.challenge.msordes.service;

import static com.compassuol.sp.challenge.msordes.Commons.OrdersConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msordes.model.dto.OrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import com.compassuol.sp.challenge.msordes.proxy.ProductProxy;
import com.compassuol.sp.challenge.msordes.proxy.ViaCepProxy;
import com.compassuol.sp.challenge.msordes.repository.OrderRepository;
import com.compassuol.sp.challenge.msordes.service.impl.OrderServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository repository;

    @Mock
    private ViaCepProxy viaCepProxy;

    @Mock
    private ProductProxy productProxy;

    @Mock
    OrderResponseDTO orderResponseDTO;

    @Test
    public void getAllOrders_WithoutStatus_ReturnsOrderResponseDTOList() {
        when(repository.findAll()).thenReturn(Collections.singletonList(ORDER));

        List<OrderResponseDTO> orderResponseDTOList = orderService.getAllOrders(null);

        assertThat(orderResponseDTOList)
                .as("List of OrderResponseDTO should not be null")
                .isNotNull();

        assertThat(orderResponseDTOList)
                .as("List of OrderResponseDTO should not be empty")
                .isNotEmpty();

        OrderResponseDTO resultOrderResponseDTO = orderResponseDTOList.get(0);

        assertThat(resultOrderResponseDTO.getId()).isEqualTo(ORDER.getId());
        assertThat(resultOrderResponseDTO.getAddress()).isEqualTo(ORDER.getAddress());
        assertThat(resultOrderResponseDTO.getDiscount()).isEqualTo(ORDER.getDiscount());
    }

    @Test
    public void getAllOrders_WithValidStatus_ReturnsOrderResponseDTOList() {
        String validStatus = "CONFIRMED";
        when(repository.findByStatus(Status.fromString(validStatus))).thenReturn(Collections.singletonList(ORDER));

        List<OrderResponseDTO> orderResponseDTOList = orderService.getAllOrders(validStatus);

        assertThat(orderResponseDTOList)
                .as("List of OrderResponseDTO should not be null")
                .isNotNull();

        assertThat(orderResponseDTOList)
                .as("List of OrderResponseDTO should not be empty")
                .isNotEmpty();

        OrderResponseDTO resultOrderResponseDTO = orderResponseDTOList.get(0);

        assertThat(resultOrderResponseDTO.getId()).isEqualTo(ORDER.getId());
        assertThat(resultOrderResponseDTO.getAddress()).isEqualTo(ORDER.getAddress());
        assertThat(resultOrderResponseDTO.getDiscount()).isEqualTo(ORDER.getDiscount());
    }

    @Test
    public void getAllOrders_WithInvalidStatus_ThrowsInvalidDataException() {
        // Arrange
        String invalidStatus = "INVALID_STATUS";
        when(repository.findByStatus(Status.fromString(invalidStatus))).thenReturn(Collections.emptyList());

        // Act and Assert
        InvalidDataException exception = org.junit.jupiter.api.Assertions.assertThrows(
                InvalidDataException.class,
                () -> orderService.getAllOrders(invalidStatus)
        );
        assertThat(exception.getMessage()).isEqualTo("Invalid status value. Allowed values are CONFIRMED, SENT, CANCELED.");
    }

    @Test
    public void createOrder_withValidData_ReturnsProduct() {
        when(productProxy.getProductById(any())).thenReturn(PRODUCTS_RESPONSE);
        when(viaCepProxy.getCEP(any())).thenReturn(ADDRESS_VIA_CEP);
        when(repository.save(any(Order.class))).thenReturn(ORDER);
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO);

        OrderResponseDTO sut = orderService.createOrder(ORDER_REQUEST_DTO);

        assertThat(sut.getId()).isEqualTo(ORDER.getId());
        assertThat(sut.getAddress()).isEqualTo(ORDER.getAddress());
        assertThat(sut.getDiscount()).isEqualTo(ORDER.getDiscount());
    }

    @Test
    public void createOrder_WithInvalidIdPaymentMethod_ThrowsException() {
        when(productProxy.getProductById(any())).thenReturn(PRODUCTS_RESPONSE);

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setAddress(ORDER_REQUEST_DTO.getAddress());
        orderRequestDTO.setProducts(ORDER_REQUEST_DTO.getProducts());
        orderRequestDTO.setPaymentMethod("Teste");

        assertDoesNotThrow(() -> {
            BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(
                    BusinessException.class,
                    () -> orderService.createOrder(orderRequestDTO)
            );
            assertThat(exception.getMessage()).isEqualTo("Allowed payment types: CREDIT_CARD, BANK_TRANSFER, CRYPTOCURRENCY, GIFT_CARD, PIX, OTHER.");
        });
    }

    @Test
    public void createOrder_WithInvalidIdCEP_ThrowsException() {
        when(productProxy.getProductById(any())).thenReturn(PRODUCTS_RESPONSE);
        when(viaCepProxy.getCEP(any())).thenThrow(FeignException.class);

        assertDoesNotThrow(() -> {
            BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(
                    BusinessException.class,
                    () -> orderService.createOrder(ORDER_REQUEST_DTO)
            );
            assertThat(exception.getMessage()).isEqualTo("Please enter a valid postal code.");
        });
    }
}
