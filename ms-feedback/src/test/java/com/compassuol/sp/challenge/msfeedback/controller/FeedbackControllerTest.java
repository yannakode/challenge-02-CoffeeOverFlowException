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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

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

    @Test
    public void getAllFeedbacks_ReturnList() {
        when(feedbackService.getAllFeedbacks()).thenReturn(FEEDBACK_RESPONSE_DTO_LIST);

        List<FeedbackResponseDto> sut = feedbackController.getAllFeedbacks();

        assertThat(sut).isEqualTo(FEEDBACK_RESPONSE_DTO_LIST);
    }

    @Test
    public void getFeedbackById_ReturnFeedback() {
        when(feedbackService.getFeedbackById(1L)).thenReturn(FEEDBACK_RESPONSE_DTO);

        ResponseEntity<FeedbackResponseDto> sut = feedbackController.getFeedbackById(1L);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(FEEDBACK_RESPONSE_DTO);
    }
    @Test
    public void deleteFeedbackById_WithValidId_ReturnsNoContent() {
        long feedbackId = 1L;

        doNothing().when(feedbackService).deleteFeedbackById(feedbackId);

        ResponseEntity<FeedbackResponseDto> sut = feedbackController.deleteFeedbackById(feedbackId);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(sut.getBody()).isNull();

        verify(feedbackService).deleteFeedbackById(feedbackId);
    }
}
