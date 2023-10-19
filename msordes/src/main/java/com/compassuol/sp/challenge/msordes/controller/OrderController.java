package com.compassuol.sp.challenge.msordes.controller;

import com.compassuol.sp.challenge.msordes.model.dto.CancelOrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msordes.model.dto.UpdateOrderRequestDTO;
import com.compassuol.sp.challenge.msordes.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private OrderServiceImpl service;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO order) {
        OrderResponseDTO orderResponse = service.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO>  updateProduct(@PathVariable(value = "id") Long id, @RequestBody @Valid UpdateOrderRequestDTO orderRequestDTO){
        orderRequestDTO.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(service.updateOrder(id, orderRequestDTO));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable("id") Long id, @RequestBody CancelOrderRequestDTO order) {
        OrderResponseDTO orderResponse = service.cancelOrder(id, order);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable(value = "id") Long id) {
        OrderResponseDTO orderById = service.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderById);
    }

}
