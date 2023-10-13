package com.compassuol.sp.challenge.msordes.model.dto;

import com.compassuol.sp.challenge.msordes.enums.PaymentMethod;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import com.compassuol.sp.challenge.msordes.model.entity.ProductOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderRequestDTO {
    private List<ProductOrderDTO> products;
    private Address address;
    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;
}
