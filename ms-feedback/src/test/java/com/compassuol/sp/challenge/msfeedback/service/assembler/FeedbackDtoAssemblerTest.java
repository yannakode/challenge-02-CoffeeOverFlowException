package com.compassuol.sp.challenge.msfeedback.service.assembler;

import static com.compassuol.sp.challenge.msfeedback.commons.FeedbacksConstants.*;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackDtoAssemblerTest {
    @InjectMocks
    FeedbackDtoAssembler assembler;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void testToDto() {
        when(modelMapper.map(FEEDBACK, FeedbackResponseDto.class)).thenReturn(FEEDBACK_RESPONSE_DTO);
        FeedbackResponseDto sut = assembler.toDto(FEEDBACK);

        assertNotNull(sut);
        assertEquals(FEEDBACK_RESPONSE_DTO, sut);
    }
    @Test
    public void testToModel() {
        when(modelMapper.map(FEEDBACK_REQUEST_DTO, Feedback.class)).thenReturn(FEEDBACK);

        Feedback sut = assembler.toModel(FEEDBACK_REQUEST_DTO);

        assertNotNull(sut);
        assertEquals(FEEDBACK, sut);
    }
}
