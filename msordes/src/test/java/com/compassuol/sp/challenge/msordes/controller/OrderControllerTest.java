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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void getAllOrders_WithValidStatus_ReturnsOrders() throws Exception {
        when(orderService.getAllOrders(ORDER.getStatus().toString())).thenReturn(List.of(ORDER_RESPONSE_DTO));

        ResponseEntity<List<OrderResponseDTO>> sut = orderController.getAllOrders(ORDER.getStatus().toString());

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(List.of(ORDER_RESPONSE_DTO));
    }

    @Test
    public void createProduct_WithValidData_ReturnsCreated() throws Exception {

        when(orderService.createOrder(ORDER_REQUEST_DTO)).thenReturn(ORDER_RESPONSE_DTO);

        ResponseEntity<OrderResponseDTO> sut = orderController.createOrder(ORDER_REQUEST_DTO);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody()).isEqualTo(ORDER_RESPONSE_DTO);
    }
}
