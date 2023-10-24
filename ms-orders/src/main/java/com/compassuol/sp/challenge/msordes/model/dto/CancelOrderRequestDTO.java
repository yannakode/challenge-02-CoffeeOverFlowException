package com.compassuol.sp.challenge.msordes.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CancelOrderRequestDTO {
    @JsonProperty("cancelReason")
    private String cancelReason;
}
