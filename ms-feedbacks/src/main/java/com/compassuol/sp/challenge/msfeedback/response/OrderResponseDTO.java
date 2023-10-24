package com.compassuol.sp.challenge.msfeedback.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private List<ProductOrderDTO> products;
    private Address address;
    private String paymentMethod;
    private Double subtotalValue;
    private Double discount;
    private Double totalValue;
    private OffsetDateTime createdDate;
    private String status;
    private OffsetDateTime updateDate;
    private OffsetDateTime cancelDate;
    private String cancelReason;

    public OrderResponseDTO(Long id, List<ProductOrderDTO> products, Address address, String paymentMethod, Double subtotalValue, Double discount, Double totalValue, OffsetDateTime createdDate, String status) {
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
}
