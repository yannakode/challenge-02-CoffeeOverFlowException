package com.compassuol.sp.challenge.msordes.service;

import static com.compassuol.sp.challenge.msordes.Commons.OrdersConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock(strictness = Mock.Strictness.LENIENT)
    private OrderRepository repository;
    @Mock
    private ViaCepProxy viaCepProxy;
    @Mock
    private ProductProxy productProxy;
    @Mock(strictness = Mock.Strictness.LENIENT)
    OrderResponseDTO orderResponseDTO;

    @Test
    public void createOrder_withValidData_ReturnsOrder() {
        when(productProxy.getProductById(anyLong())).thenReturn(PRODUCTS_RESPONSE);
        when(viaCepProxy.getCEP(anyString())).thenReturn(ADDRESS_VIA_CEP);
        when(repository.save(any(Order.class))).thenReturn(ORDER);
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO);

        OrderResponseDTO sut = orderService.createOrder(ORDER_REQUEST_DTO);

        assertThat(sut.getId()).isEqualTo(ORDER.getId());
        assertThat(sut.getAddress()).isEqualTo(ORDER.getAddress());
        assertThat(sut.getDiscount()).isEqualTo(ORDER.getDiscount());
    }

    @Test
    public void createOrder_WithInvalidIdProduct_ThrowsException() {
        when(productProxy.getProductById(anyLong())).thenThrow(FeignException.class);

        try {
            orderService.createOrder(ORDER_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("No product was found for the product_id provided.").isEqualTo(e.getMessage());
        }
    }

    @Test
    public void createOrder_WithInvalidIdPaymentMethod_ThrowsException() {

        when(productProxy.getProductById(anyLong())).thenReturn(PRODUCTS_RESPONSE);

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setAddress(ORDER_REQUEST_DTO.getAddress());
        orderRequestDTO.setProducts(ORDER_REQUEST_DTO.getProducts());
        orderRequestDTO.setPaymentMethod("Teste");

        try {
            OrderResponseDTO sut = orderService.createOrder(orderRequestDTO);
        } catch (BusinessException e) {
            assertThat("Allowed payment types: CREDIT_CARD, BANK_TRANSFER, CRYPTOCURRENCY, GIFT_CARD, PIX, OTHER.").isEqualTo(e.getMessage());
        }
    }

    @Test
    public void createOrder_WithInvalidIdCEP_ThrowsException() {
        when(productProxy.getProductById(anyLong())).thenReturn(PRODUCTS_RESPONSE);
        when(viaCepProxy.getCEP(ORDER_REQUEST_DTO.getAddress().getPostalCode())).thenThrow(FeignException.class);

        try {
            OrderResponseDTO sut = orderService.createOrder(ORDER_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("Please enter a valid postal code.").isEqualTo(e.getMessage());
        }
    }

    @Test
    public void update_withValidData_ReturnsOrder() {
        when(productProxy.getProductById(anyLong())).thenReturn(PRODUCTS_RESPONSE);
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER));
        when(viaCepProxy.getCEP(anyString())).thenReturn(ADDRESS_VIA_CEP);
        when(repository.save(any(Order.class))).thenReturn(ORDER);
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO);

        OrderResponseDTO sut = orderService.updateOrder(1L, UPDATE_ORDER_REQUEST_DTO);

        assertThat(sut.getId()).isEqualTo(ORDER.getId());
        assertThat(sut.getAddress()).isEqualTo(ORDER.getAddress());
        assertThat(sut.getDiscount()).isEqualTo(ORDER.getDiscount());
    }

    @Test
    public void updateOrder_WithInvalidStatus_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER));

        try {
            OrderResponseDTO sut = orderService.updateOrder(1L, UPDATE_ORDER_REQUEST_DTO_INVALID_STATUS);
        } catch (BusinessException e) {
            assertThat("Status of requests allowed for this operation: CONFIRMED or SENT").isEqualTo(e.getMessage());
        }
    }
    @Test
    public void updateOrder_WithInvalidStatusCanceled_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER));

        try {
            OrderResponseDTO sut = orderService.updateOrder(1L, UPDATE_ORDER_REQUEST_DTO_INVALID_STATUS_CANCELED);
        } catch (BusinessException e) {
            assertThat("If you wish to cancel the order, use the endpoint: /orders/{id}/cancel").isEqualTo(e.getMessage());
        }
    }

    @Test
    public void cancelOrder_withValidData_ReturnsOrder() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER));
        when(repository.save(any(Order.class))).thenReturn(ORDER);
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO_CANCELED);

        OrderResponseDTO sut = orderService.cancelOrder(1L, CANCEL_ORDER_REQUEST_DTO);

        assertThat(sut.getId()).isEqualTo(ORDER.getId());
        assertThat(sut.getCancelReason()).isEqualTo(CANCEL_ORDER_REQUEST_DTO.getCancelReason());
        assertThat(sut.getDiscount()).isEqualTo(ORDER.getDiscount());
    }

    @Test
    public void cancelOrder_withInvalidDays_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER_INVALID_DAYS));
        when(repository.save(any(Order.class))).thenReturn(ORDER);
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO_CANCELED);

        try {
            OrderResponseDTO sut = orderService.cancelOrder(1L, CANCEL_ORDER_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("Tan order cannot be canceled if it is more than 90 days old").isEqualTo(e.getMessage());
        }
    }
    @Test
    public void cancelOrder_withInvalidStatusSent_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER_INVALID_STATUS_SENT));
        when(repository.save(any(Order.class))).thenReturn(ORDER);
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO_CANCELED);

        try {
            OrderResponseDTO sut = orderService.cancelOrder(1L, CANCEL_ORDER_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("An order can only be canceled if the status is other than SENT").isEqualTo(e.getMessage());
        }
    }
    @Test
    public void cancelOrder_withInvalidStatusCanceled_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER_INVALID_STATUS_CANCELED));
        when(repository.save(any(Order.class))).thenReturn(ORDER);
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO_CANCELED);

        try {
            OrderResponseDTO sut = orderService.cancelOrder(1L, CANCEL_ORDER_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("This order is now canceled").isEqualTo(e.getMessage());
        }
    }

    @Test
    public void getOrderById_withValidData_ReturnsOrder() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER));
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO);

        OrderResponseDTO sut = orderService.getOrderById(1L);

        assertThat(sut.getId()).isEqualTo(ORDER_RESPONSE_DTO.getId());
        assertThat(sut.getDiscount()).isEqualTo(ORDER_RESPONSE_DTO.getDiscount());
        assertThat(sut.getPaymentMethod()).isEqualTo(ORDER_RESPONSE_DTO.getPaymentMethod());
    }

    @Test
    public void getOrderById_withInvalidData_ () {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER));
        when(orderResponseDTO.toDTO(any(Order.class))).thenReturn(ORDER_RESPONSE_DTO);

        try {
            OrderResponseDTO sut = orderService.getOrderById(1L);
        } catch (EntityNotFoundException e) {
            assertThat("products not found!").isEqualTo(e.getMessage());
        }
    }
}
