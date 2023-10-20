package com.compassuol.sp.challenge.msfeedback.commons;

import com.compassuol.sp.challenge.msfeedback.enums.Scales;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import com.compassuol.sp.challenge.msfeedback.response.Address;
import com.compassuol.sp.challenge.msfeedback.response.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.response.ProductOrderDTO;

import java.time.OffsetDateTime;
import java.util.List;

public class FeedbacksConstants {
    public static final Address ADDRESS = new Address("Street Name", 10, "", "Petrolina", "PE", "56332016");
    public static final ProductOrderDTO PRODUCT_ORDER_DTO = new ProductOrderDTO(1L, 5);
    public static final OrderResponseDTO ORDER_RESPONSE_DTO =  new OrderResponseDTO(1L, List.of(PRODUCT_ORDER_DTO), ADDRESS, "PIX", 889.98, 0.05, 845.48, OffsetDateTime.now(), "CONFIRMED");
    public static final OrderResponseDTO ORDER_RESPONSE_DTO_INVALID_STATUS =  new OrderResponseDTO(1L, List.of(PRODUCT_ORDER_DTO), ADDRESS, "PIX", 889.98, 0.05, 845.48, OffsetDateTime.now(), "TEST");
    public static final FeedbackRequestDto FEEDBACK_REQUEST_DTO = new FeedbackRequestDto("VERY_SATISFIED", "No comment", 1L);
    public static final FeedbackRequestDto FEEDBACK_REQUEST_DTO_INVALID_SCALE = new FeedbackRequestDto("TEST", "No comment", 1L);
    public static final FeedbackResponseDto FEEDBACK_RESPONSE_DTO = new FeedbackResponseDto(1L, "VERY_SATISFIED", "No comment", 1L);
    public static final Feedback FEEDBACK = new Feedback(1L, Scales.VERY_SATISFIED, "No comment", 1L);
}
