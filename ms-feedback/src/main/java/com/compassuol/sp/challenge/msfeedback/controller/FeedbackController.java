package com.compassuol.sp.challenge.msfeedback.controller;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.service.impl.FeedbackServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private FeedbackServiceImpl feedbackService;
    @PostMapping
    public ResponseEntity<FeedbackResponseDto> createFeedback(@RequestBody FeedbackRequestDto feedBackRequestDto) {
        FeedbackResponseDto feedbackResponse = feedbackService.createFeedback(feedBackRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackResponse);
    }
}
