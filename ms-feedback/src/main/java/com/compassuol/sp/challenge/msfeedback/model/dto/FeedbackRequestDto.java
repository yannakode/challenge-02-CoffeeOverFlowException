package com.compassuol.sp.challenge.msfeedback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDto {
    private String scale;
    private String comment;
    @JsonProperty("order_id")
    private Long orderId;
}
