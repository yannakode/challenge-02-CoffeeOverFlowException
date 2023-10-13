package com.compassuol.sp.challenge.msordes.service.impl;

import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msordes.model.dto.OrderRequestDTO;
import com.compassuol.sp.challenge.msordes.model.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import com.compassuol.sp.challenge.msordes.model.entity.ProductOrder;
import com.compassuol.sp.challenge.msordes.proxy.ProductProxy;
import com.compassuol.sp.challenge.msordes.proxy.ViaCepProxy;
import com.compassuol.sp.challenge.msordes.repository.OrderRepository;
import com.compassuol.sp.challenge.msordes.response.AddressViaCep;
import com.compassuol.sp.challenge.msordes.response.Products;
import com.compassuol.sp.challenge.msordes.service.OrderService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ViaCepProxy viaCepProxy;
    private final ProductProxy productProxy;

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return null;
    }

    @Override
    public OrderResponseDTO getOrderById(long orderId) {
        return null;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Order savedOrder = repository.save(createOrderFromRequest(orderRequestDTO));
        return OrderResponseDTO.toDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        return null;
    }

    @Override
    public boolean deleteOrder() {
        return false;
    }

    public Order createOrderFromRequest(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();

        orderRequestDTO.getProducts().forEach(e -> {
            if(e.getQuantity() == null || e.getQuantity() == 0|| e.getProductId() == null || e.getProductId() == 0) throw new InvalidDataException("Please review the products. The product_id and quantity fields cannot be empty.", "products");
            try{
                Products productById = productProxy.getProductById(e.getProductId());
            } catch (FeignException ex) {
               throw new BusinessException("No product was found for the product_id provided.");
            }
        });

        try{
            AddressViaCep cep = viaCepProxy.getCEP(orderRequestDTO.getAddress().getPostalCode());
            order.setAddress(orderRequestDTO.getAddress());
            order.getAddress().setComplement(cep.getComplemento());
            order.getAddress().setCity(cep.getLocalidade());
            order.getAddress().setState(cep.getUf());
        } catch (FeignException ex) {
            throw new BusinessException("Please enter a valid postal code.");
        }

        order.setPaymentMethod(orderRequestDTO.getPaymentMethod());
        order.setCreatedDate(new Date());
        order.setSubtotalValue(100.0);
        order.setDiscount(0.5);
        order.setTotalValue(95.0);
        order.setStatus(Status.CONFIRMED);

        orderRequestDTO.getProducts().forEach(e -> {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrder(order);
            productOrder.setProductId(e.getProductId());
            productOrder.setQuantity(e.getQuantity());
            order.getProducts().add(productOrder);
        });
        return order;
    };
}
