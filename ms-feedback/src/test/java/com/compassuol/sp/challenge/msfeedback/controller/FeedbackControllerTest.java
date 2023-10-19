package com.compassuol.sp.challenge.msfeedback.controller;

import static com.compassuol.sp.challenge.msfeedback.commons.FeedbacksConstants.*;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.service.impl.FeedbackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackControllerTest {
    @InjectMocks
    private FeedbackController feedbackController;
    @Mock
    private FeedbackServiceImpl feedbackService;

    @Test
    public void createFeedback_WithValidData_ReturnsCreated() {

        when(feedbackService.createFeedback(FEEDBACK_REQUEST_DTO)).thenReturn(FEEDBACK_RESPONSE_DTO);

        ResponseEntity<FeedbackResponseDto> sut = feedbackController.createFeedback(FEEDBACK_REQUEST_DTO);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody()).isEqualTo(FEEDBACK_RESPONSE_DTO);
    }
}
