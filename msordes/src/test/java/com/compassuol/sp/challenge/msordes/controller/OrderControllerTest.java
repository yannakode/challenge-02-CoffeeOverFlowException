package com.compassuol.sp.challenge.msordes.controller;

import static com.compassuol.sp.challenge.msordes.Commons.OrdersConstants.*;

import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msordes.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void createProduct_WithValidData_ReturnsCreated() throws Exception {

        when(orderService.createOrder(ORDER_REQUEST_DTO)).thenReturn(ORDER_RESPONSE_DTO);

        ResponseEntity<OrderResponseDTO> sut = orderController.createOrder(ORDER_REQUEST_DTO);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody()).isEqualTo(ORDER_RESPONSE_DTO);
    }

    @Test
    public void updateProduct_WithValidData_ReturnsCreated() throws Exception {

        when(orderService.updateOrder(1L, UPDATE_ORDER_REQUEST_DTO)).thenReturn(ORDER_RESPONSE_DTO);

        ResponseEntity<OrderResponseDTO> sut = orderController.updateProduct(1L, UPDATE_ORDER_REQUEST_DTO);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(ORDER_RESPONSE_DTO);
    }

    @Test
    public void getOrderById_WithValidData_ReturnOrder() throws Exception {

        when(orderService.getOrderById(1L)).thenReturn(ORDER_RESPONSE_DTO);

        ResponseEntity<OrderResponseDTO> sut = orderController.getOrderById(1L);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(ORDER_RESPONSE_DTO);
    }

    @Test
    public void cancelOrder_WithValidData_ReturnOrder() throws Exception {

        when(orderService.cancelOrder(1L, CANCEL_ORDER_REQUEST_DTO)).thenReturn(ORDER_RESPONSE_DTO_CANCELED);

        ResponseEntity<OrderResponseDTO> sut = orderController.cancelOrder(1L, CANCEL_ORDER_REQUEST_DTO);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(ORDER_RESPONSE_DTO_CANCELED);
    }
}
