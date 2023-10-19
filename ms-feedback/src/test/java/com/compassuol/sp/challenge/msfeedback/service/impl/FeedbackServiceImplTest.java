package com.compassuol.sp.challenge.msfeedback.service.impl;




import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import com.compassuol.sp.challenge.msfeedback.service.assembler.FeedbackDtoAssembler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.compassuol.sp.challenge.msfeedback.commons.FeedBackConstants.*;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {
    @InjectMocks
    private FeedbackServiceImpl feedbackService;
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private FeedbackDtoAssembler feedbackDtoAssembler;



    @Test
    void getAllFeedbacks_ReturnsFeedback() {
        when(feedbackRepository.findAll()).thenReturn(List.of(FEEDBACK));
        when(feedbackDtoAssembler.toDto(FEEDBACK)).thenReturn(PRODUCT_RESPONSE_DTO);

        List<FeedbackResponseDto> sut = feedbackService.getAllFeedbacks();
    }
}