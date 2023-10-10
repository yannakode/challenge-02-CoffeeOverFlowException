package com.compassuol.sp.challenge.msproducts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private Double value;
    private String description;
    public ProductResponseDto() {
    }
}
