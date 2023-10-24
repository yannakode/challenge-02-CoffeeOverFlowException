package com.compassuol.sp.challenge.msfeedback.controller;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.service.impl.FeedbackServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping({"/{id}"})
    public ResponseEntity<FeedbackResponseDto> deleteFeedbackById(@PathVariable Long id) {
        feedbackService.deleteFeedbackById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FeedbackResponseDto> getAllFeedbacks(){
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> getFeedbackById(@PathVariable long id){
        return ResponseEntity.ok(
                feedbackService.getFeedbackById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> putFeedback(@PathVariable long id, @RequestBody FeedbackRequestDto body){
        return ResponseEntity.ok(
                feedbackService.updateFeedback(id, body)
        );
    }

}
