package com.compassuol.sp.challenge.msordes.service;

import com.compassuol.sp.challenge.msordes.model.dto.OrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders(String status);
    OrderResponseDTO getOrderById(long orderId);
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateOrder(Long orderId , OrderRequestDTO orderRequestDTO);
    boolean deleteOrder();
}
