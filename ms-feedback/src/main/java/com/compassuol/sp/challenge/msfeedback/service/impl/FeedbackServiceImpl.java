package com.compassuol.sp.challenge.msfeedback.service.impl;

import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import com.compassuol.sp.challenge.msfeedback.proxy.OrderProxy;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import com.compassuol.sp.challenge.msfeedback.response.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.service.FeedbackService;
import com.compassuol.sp.challenge.msfeedback.service.assembler.FeedbackDtoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {


    private final FeedbackDtoAssembler assembler;
    private final FeedbackRepository repository;
    private final OrderProxy proxy;

    @Override
    public List<FeedbackResponseDto> getAllFeedbacks() {
        List<Feedback> responseFeedBack = repository.findAll();
        List<FeedbackResponseDto> feedBackList = new ArrayList<>();

        responseFeedBack.forEach(feedback -> {
            feedBackList.add(assembler.toDto(feedback));
        });
        return feedBackList;
    }

    @Override
    public FeedbackResponseDto getFeedbackById(long feedbackId) {
        return null;
    }

    @Override
    public FeedbackResponseDto createFeedback(FeedbackRequestDto feedBackRequestDto) {
        OrderResponseDTO orderByIdResponse = proxy.getOrderById(feedBackRequestDto.getOrderId());

        Feedback model = assembler.toModel(feedBackRequestDto);
        Feedback save = repository.save(model);

        return assembler.toDto(save);
    }

    @Override
    public FeedbackResponseDto updateFeedback(Long feedbackId, FeedbackRequestDto feedBackRequestDto) {
        return null;
    }

    @Override
    public void deleteFeedbackById(long id) {

    }
}
