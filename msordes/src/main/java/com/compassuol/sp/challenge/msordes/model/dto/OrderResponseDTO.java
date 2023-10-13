package com.compassuol.sp.challenge.msordes.model.dto;

import com.compassuol.sp.challenge.msordes.enums.PaymentMethod;
import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Date createdDate;
    private Status status;

    public static OrderResponseDTO toDTO(Order order) {
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
