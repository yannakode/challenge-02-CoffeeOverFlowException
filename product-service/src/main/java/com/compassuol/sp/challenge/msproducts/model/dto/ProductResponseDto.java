package com.compassuol.sp.challenge.msproducts.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponseDto {
    private Long id;
    private String name;
    private Double value;
    private String description;
}
