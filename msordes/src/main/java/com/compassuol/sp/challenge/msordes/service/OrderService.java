package com.compassuol.sp.challenge.msordes.service;

import com.compassuol.sp.challenge.msordes.model.dto.CancelOrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msordes.model.dto.UpdateOrderRequestDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(long orderId);
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateOrder(Long orderId , UpdateOrderRequestDTO orderRequestDTO);
    OrderResponseDTO cancelOrder(Long orderId, CancelOrderRequestDTO cancelOrderRequestDTO);
}
