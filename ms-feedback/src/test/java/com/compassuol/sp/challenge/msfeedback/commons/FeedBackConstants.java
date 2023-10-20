package com.compassuol.sp.challenge.msfeedback.commons;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import com.compassuol.sp.challenge.msfeedback.enums.Scales;

public class FeedBackConstants {
    public static final Feedback FEEDBACK = new Feedback(1L,Scales.SATISFIED,"Comment",3L);
    public static final FeedbackResponseDto PRODUCT_RESPONSE_DTO = new FeedbackResponseDto(1L, "SATISFIED", "Product name", 2L);
}