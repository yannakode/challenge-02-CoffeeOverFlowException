package com.compassuol.sp.challenge.msfeedback.service.impl;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedBackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedBackResponseDto;
import com.compassuol.sp.challenge.msfeedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedBackImpl implements FeedbackService {

    @Override
    public List<FeedBackResponseDto> getAllProducts() {
        return null;
    }

    @Override
    public FeedBackResponseDto getProductById(long feedbackId) {
        return null;
    }

    @Override
    public FeedBackResponseDto createProduct(FeedBackRequestDto feedBackRequestDto) {
        return null;
    }

    @Override
    public FeedBackResponseDto updateProduct(Long feedbackId, FeedBackRequestDto feedBackRequestDto) {
        return null;
    }

    @Override
    public void deleteProductById(long id) {

    }
}
