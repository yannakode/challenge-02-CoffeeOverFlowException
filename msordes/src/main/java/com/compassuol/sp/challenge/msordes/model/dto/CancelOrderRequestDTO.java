package com.compassuol.sp.challenge.msordes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CancelOrderRequestDTO {
    private String cancelReason;
}
