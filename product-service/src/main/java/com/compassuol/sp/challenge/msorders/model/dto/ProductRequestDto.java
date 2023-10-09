package com.compassuol.sp.challenge.msorders.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String name;
    private String description;
    private double value;
}
