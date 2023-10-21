package com.compassuol.sp.challenge.msfeedback.service.assembler;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackDtoAssembler {
    private final ModelMapper modelMapper;

    public FeedbackResponseDto toDto(Feedback feedback){
        return modelMapper.map(feedback, FeedbackResponseDto.class);
    }
    public Feedback toModel(FeedbackRequestDto feedBackRequestDto){
        return modelMapper.map(feedBackRequestDto,Feedback.class);
    }
}
