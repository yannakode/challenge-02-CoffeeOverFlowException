package com.compassuol.sp.challenge.msfeedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackRequestDto {

    private String scale;
    private String comment;
    private Long orderId;
}
