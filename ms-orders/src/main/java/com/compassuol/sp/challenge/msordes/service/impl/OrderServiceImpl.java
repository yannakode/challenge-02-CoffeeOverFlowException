package com.compassuol.sp.challenge.msordes.service.impl;

import com.compassuol.sp.challenge.msordes.enums.PaymentMethod;
import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msordes.model.dto.CancelOrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msordes.model.dto.UpdateOrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import com.compassuol.sp.challenge.msordes.model.entity.ProductOrder;
import com.compassuol.sp.challenge.msordes.proxy.ProductProxy;
import com.compassuol.sp.challenge.msordes.proxy.ViaCepProxy;
import com.compassuol.sp.challenge.msordes.repository.OrderRepository;
import com.compassuol.sp.challenge.msordes.response.AddressViaCep;
import com.compassuol.sp.challenge.msordes.response.Products;
import com.compassuol.sp.challenge.msordes.service.OrderService;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ViaCepProxy viaCepProxy;
    private final ProductProxy productProxy;

    @Override
    public List<OrderResponseDTO> getAllOrders(String status) {
        List<Order> orders;

        if (status != null) {
            validateStatus(status);
            orders = repository.findByStatus(Status.fromString(status));
            return orders.stream()
                    .map(e -> new OrderResponseDTO().toDTO(e))
                    .collect(Collectors.toList());
        }

        orders = repository.findAll();
        return orders.stream()
                .map(e -> new OrderResponseDTO().toDTO(e))
                .collect(Collectors.toList());
    }

    private void validateStatus(String status) {
        if (status != null) {
            try {
                Status.fromString(status);
            } catch (InvalidDataException e) {
                throw new InvalidDataException("Invalid status value. Allowed values are CONFIRMED, SENT, CANCELED.", "status");
            }
        }
    }

    @Override
    public OrderResponseDTO getOrderById(long orderId) {
        Order orderResponse = repository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        return new OrderResponseDTO().toDTO(orderResponse);
    }

    @Override
    public OrderResponseDTO updateOrder(Long orderId, UpdateOrderRequestDTO orderRequestDTO) {
        Order order = getOrder(orderRequestDTO);

        order.setId(((UpdateOrderRequestDTO) orderRequestDTO).getId());
        order.setUpdateDate(OffsetDateTime.now());

        Order savedOrder = repository.save(order);
        return new OrderResponseDTO().toDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO cancelOrder(Long orderId, CancelOrderRequestDTO cancelOrderRequestDTO) {
        Optional<Order> result = repository.findById(orderId);
        if (result.isEmpty()) throw new BusinessException("The provided ID does not exist");
        Order order = result.get();

        if(order.getStatus() == Status.SENT) throw new BusinessException("An order can only be canceled if the status is other than SENT");
        if(order.getStatus() == Status.CANCELED) throw new BusinessException("This order is now canceled");

        OffsetDateTime deadline = OffsetDateTime.now().minusDays(90);

        if(order.getCreatedDate().isBefore(deadline)) throw new BusinessException("Tan order cannot be canceled if it is more than 90 days old");

        order.setStatus(Status.CANCELED);
        order.setCancelDate(OffsetDateTime.now());
        order.setCancelReason(cancelOrderRequestDTO.getCancelReason());

        Order savedOrder = repository.save(order);
        return new OrderResponseDTO().toDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = getOrder(orderRequestDTO);
        order.setCreatedDate(OffsetDateTime.now());

        Order savedOrder = repository.save(order);
        return new OrderResponseDTO().toDTO(savedOrder);
    }

    private Order getOrder(OrderRequestDTO orderRequestDTO) {
        Order order = null;

        if (orderRequestDTO instanceof UpdateOrderRequestDTO) {
            Optional<Order> result = repository.findById(((UpdateOrderRequestDTO) orderRequestDTO).getId());
            if (result.isEmpty()) throw new BusinessException("The provided ID does not exist");
            order = result.get();

            try {
                Status status = Status.valueOf(((UpdateOrderRequestDTO) orderRequestDTO).getStatus());
                if (status == Status.CONFIRMED || status == Status.SENT) order.setStatus(status); else {throw new BusinessException("If you wish to cancel the order, use the endpoint: /orders/{id}/cancel");};
            } catch (IllegalArgumentException ex) {
                throw new BusinessException("Status of requests allowed for this operation: CONFIRMED or SENT");
            }
        } else {
            order = new Order();
            order.setStatus(Status.CONFIRMED);
        }

        AtomicReference<Double> totalValue = new AtomicReference<>(0.0);

        orderRequestDTO.getProducts().forEach(e -> {
            if(e.getQuantity() == null || e.getQuantity() == 0|| e.getProductId() == null || e.getProductId() == 0) throw new InvalidDataException("Please review the products. The product_id and quantity fields cannot be empty.", "products");
            try{
                Products productById = productProxy.getProductById(e.getProductId());
                totalValue.updateAndGet(v -> v + (productById.getValue() * e.getQuantity()));
            } catch (FeignException ex) {
                throw new BusinessException("No product was found for the product_id provided.");
            }
        });

        try{
            PaymentMethod paymentMethod = PaymentMethod.valueOf(orderRequestDTO.getPaymentMethod());
            order.setPaymentMethod(paymentMethod);
            if(paymentMethod == PaymentMethod.PIX) order.setDiscount(0.05); else {order.setDiscount(0.0);};
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Allowed payment types: CREDIT_CARD, BANK_TRANSFER, CRYPTOCURRENCY, GIFT_CARD, PIX, OTHER.");
        }

        BigDecimal totalValueBigDecimal = new BigDecimal(totalValue.get().toString());
        BigDecimal operation = totalValueBigDecimal.subtract(totalValueBigDecimal.multiply(new BigDecimal(order.getDiscount().toString()))).setScale(2, RoundingMode.HALF_UP);

        order.setSubtotalValue(totalValue.get());
        order.setTotalValue(operation.doubleValue());

        try{
            AddressViaCep cep = viaCepProxy.getCEP(orderRequestDTO.getAddress().getPostalCode());
            order.setAddress(orderRequestDTO.getAddress());
            order.getAddress().setComplement(cep.getComplemento());
            order.getAddress().setCity(cep.getLocalidade());
            order.getAddress().setState(cep.getUf());
        } catch (FeignException ex) {
            throw new BusinessException("Please enter a valid postal code.");
        }

        Order finalOrder = order;
        finalOrder.setProducts(new ArrayList<>());
        orderRequestDTO.getProducts().forEach(e -> {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrder(finalOrder);
            productOrder.setProductId(e.getProductId());
            productOrder.setQuantity(e.getQuantity());
            finalOrder.getProducts().add(productOrder);
        });
        return order;
    }

}
