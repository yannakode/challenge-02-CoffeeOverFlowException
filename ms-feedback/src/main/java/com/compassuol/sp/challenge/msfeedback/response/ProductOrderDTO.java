package com.compassuol.sp.challenge.msfeedback.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOrderDTO {
    @JsonProperty("product_id")
    private Long productId;
    private Integer quantity;

}


