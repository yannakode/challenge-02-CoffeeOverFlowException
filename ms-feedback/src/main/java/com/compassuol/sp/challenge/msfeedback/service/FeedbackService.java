package com.compassuol.sp.challenge.msfeedback.service;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedBackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedBackResponseDto;

import java.util.List;

public interface FeedbackService {
    List<FeedBackResponseDto> getAllProducts();
    FeedBackResponseDto getProductById(long feedbackId);
    FeedBackResponseDto createProduct(FeedBackRequestDto feedBackRequestDto);
    FeedBackResponseDto updateProduct(Long feedbackId , FeedBackRequestDto feedBackRequestDto);
    void deleteProductById(long id);
}
