package com.compassuol.sp.challenge.msproducts.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    private String name;
    private Double value;
    private String description;
}
