package com.compassuol.sp.challenge.msordes.model.dto;

import com.compassuol.sp.challenge.msordes.enums.PaymentMethod;
import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private List<ProductOrderDTO> products;
    private Address address;
    private PaymentMethod paymentMethod;
    private Double subtotalValue;
    private Double discount;
    private Double totalValue;
    private OffsetDateTime createdDate;
    private Status status;
    private OffsetDateTime updateDate;
    private OffsetDateTime cancelDate;
    private String cancelReason;

    public OrderResponseDTO(Long id, List<ProductOrderDTO> products, Address address, PaymentMethod paymentMethod, Double subtotalValue, Double discount, Double totalValue, OffsetDateTime createdDate, Status status) {
        this.id = id;
        this.products = products;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.subtotalValue = subtotalValue;
        this.discount = discount;
        this.totalValue = totalValue;
        this.createdDate = createdDate;
        this.status = status;
    }

    public OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());

        List<ProductOrderDTO> products= new ArrayList<>();
        order.getProducts().forEach(e -> {
            ProductOrderDTO productOrderDTO = new ProductOrderDTO();
            productOrderDTO.setQuantity(e.getQuantity());
            productOrderDTO.setProductId(e.getProductId());
            products.add(productOrderDTO);
        });
        orderResponseDTO.setAddress(order.getAddress());
        orderResponseDTO.setProducts(products);
        orderResponseDTO.setPaymentMethod(order.getPaymentMethod());
        orderResponseDTO.setDiscount(order.getDiscount());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setCreatedDate(order.getCreatedDate());
        orderResponseDTO.setSubtotalValue(order.getSubtotalValue());
        orderResponseDTO.setTotalValue(order.getTotalValue());

        return orderResponseDTO;
    }
}
