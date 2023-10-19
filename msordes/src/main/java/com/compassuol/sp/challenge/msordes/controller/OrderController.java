package com.compassuol.sp.challenge.msordes.controller;

import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.model.dto.OrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msordes.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private OrderServiceImpl service;

    @GetMapping
    ResponseEntity<List<OrderResponseDTO>> getAllOrders(@RequestParam(required = false) String status){
        List<OrderResponseDTO> orders = service.getAllOrders(status);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO order) {
        OrderResponseDTO product = service.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


}