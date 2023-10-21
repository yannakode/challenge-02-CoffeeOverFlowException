package com.compassuol.sp.challenge.msfeedback.service;


import static com.compassuol.sp.challenge.msfeedback.commons.FeedbacksConstants.*;

import com.compassuol.sp.challenge.msfeedback.commons.FeedbacksConstants;
import com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.proxy.OrderProxy;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import com.compassuol.sp.challenge.msfeedback.response.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.service.assembler.FeedbackDtoAssembler;
import com.compassuol.sp.challenge.msfeedback.service.impl.FeedbackServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {
    @InjectMocks
    private FeedbackServiceImpl feedbackService;
    @Mock
    private FeedbackRepository repository;
    @Mock
    private OrderProxy proxy;
    @Mock
    OrderResponseDTO orderResponseDTO;
    @Mock
    FeedbackDtoAssembler assembler;


    @Test
    public void createFeedback_withValidData_ReturnsFeedback() {
        when(assembler.toDto(FEEDBACK)).thenReturn(FEEDBACK_RESPONSE_DTO);
        when(assembler.toModel(FEEDBACK_REQUEST_DTO)).thenReturn(FEEDBACK);
        when(proxy.getOrderById(1L)).thenReturn(ORDER_RESPONSE_DTO);
        when(repository.save(FEEDBACK)).thenReturn(FEEDBACK);

        FeedbackResponseDto sut = feedbackService.createFeedback(FEEDBACK_REQUEST_DTO);

        assertThat(sut).isEqualTo(FEEDBACK_RESPONSE_DTO);
    }

    @Test
    public void createFeedback_withInvalidOrderId_ThrowsException() {
        when(proxy.getOrderById(1L)).thenThrow(FeignException.class);

        try {
            feedbackService.createFeedback(FEEDBACK_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("No order was found for the order_id provided").isEqualTo(e.getMessage());
        }

    }

    @Test
    public void createFeedback_withInvalidFeedbackScale_ThrowsException() {
        when(proxy.getOrderById(1L)).thenReturn(ORDER_RESPONSE_DTO);

        try {
            feedbackService.createFeedback(FEEDBACK_REQUEST_DTO_INVALID_SCALE);
        } catch (BusinessException e) {
            assertThat("The allowed satisfaction levels (scale) are: VERY_DISSATISFIED, DISSATISFIED, NEUTRAL, SATISFIED, VERY_ SATISFIED").isEqualTo(e.getMessage());
        }
    }

    @Test
    public void createFeedback_withInvalidOrderStatus_ThrowsException() {
        when(proxy.getOrderById(1L)).thenReturn(ORDER_RESPONSE_DTO_INVALID_STATUS);

        try {
            feedbackService.createFeedback(FEEDBACK_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("It is not allowed to leave feedback on orders with status CANCELED").isEqualTo(e.getMessage());
        }
    }
    @Test
    void getAllFeedbacks_ReturnsFeedback() {
        when(repository.findAll()).thenReturn(List.of(FEEDBACK));
        when(assembler.toDto(FEEDBACK)).thenReturn(FEEDBACK_RESPONSE_DTO);

        List<FeedbackResponseDto> sut = feedbackService.getAllFeedbacks();
    }

    @Test
    void getAllFeedbacks_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        try {
            feedbackService.getAllFeedbacks();
        } catch (BusinessException ex) {
            assertThat("No feedback was found.").isEqualTo(ex.getMessage());
        }
    }

    @Test
    public void deleteFeedbackById_withValidId_DeletesFeedback() {
        long feedbackId = 1L;
        when(repository.findById(feedbackId)).thenReturn(Optional.of(FEEDBACK));

        feedbackService.deleteFeedbackById(feedbackId);

        verify(repository, times(1)).deleteById(feedbackId);
    }
    @Test
    public void deleteFeedbackById_withInvalidId_ThrowsException() {
        long invalidId = 0L;

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            feedbackService.deleteFeedbackById(invalidId);
        });

        assertThat(exception.getMessage()).isEqualTo("Id value must be not null and greater than zero");

        verify(repository, never()).deleteById(invalidId);
    }
}
