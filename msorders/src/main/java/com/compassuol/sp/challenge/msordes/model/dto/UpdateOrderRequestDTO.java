package com.compassuol.sp.challenge.msordes.model.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateOrderRequestDTO extends OrderRequestDTO{
    private Long id;
    private String status;

    public UpdateOrderRequestDTO(Long id, List<ProductOrderDTO> products, Address address, String paymentMethod, String status) {
        super(products, address, paymentMethod);
        this.id = id;
        this.status = status;
    }

}
