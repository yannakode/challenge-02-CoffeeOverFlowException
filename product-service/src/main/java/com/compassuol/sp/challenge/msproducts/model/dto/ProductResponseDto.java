package com.compassuol.sp.challenge.msproducts.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String name;
    private double value;
    private String description;
}
