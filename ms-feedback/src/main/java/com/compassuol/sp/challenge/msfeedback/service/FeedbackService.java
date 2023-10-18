package com.compassuol.sp.challenge.msfeedback.service;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackResponseDto> getAllFeedbacks();
    FeedbackResponseDto getFeedbackById(long feedbackId);
    FeedbackResponseDto createFeedback(FeedbackRequestDto feedBackRequestDto);
    FeedbackResponseDto updateFeedback(Long feedbackId , FeedbackRequestDto feedBackRequestDto);
    void deleteFeedbackById(long id);
}
