package com.compassuol.sp.challenge.msproducts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProductRequestDto {

    private String name;
    private Double value;
    private String description;
}
