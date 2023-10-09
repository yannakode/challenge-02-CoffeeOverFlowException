package com.compassuol.sp.challenge.msproducts.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    @NotBlank(message = "O nome não pode estar em branco.")
    private String name;
    @NotBlank(message = "A descrição não pode estar em branco.")
    @Min(value = 0, message = "O valor deve ser um número positivo.")
    private double value;
    @Size(min = 10, message = "A descrição deve ter pelo menos 10 caracteres.")
    private String description;
}
