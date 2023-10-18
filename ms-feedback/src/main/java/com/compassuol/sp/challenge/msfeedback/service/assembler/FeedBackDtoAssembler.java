package com.compassuol.sp.challenge.msfeedback.service.assembler;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedBackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedBackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedBackDtoAssembler {
    private final ModelMapper modelMapper;

    public FeedBackResponseDto toDto(Feedback feedback){
        return modelMapper.map(feedback,FeedBackResponseDto.class);
    }
    public Feedback toModel(FeedBackRequestDto feedBackRequestDto){
        return modelMapper.map(feedBackRequestDto,Feedback.class);
    }
}
