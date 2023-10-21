package com.compassuol.sp.challenge.msfeedback.service.impl;

import com.compassuol.sp.challenge.msfeedback.enums.Scales;
import com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackRequestDto;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import com.compassuol.sp.challenge.msfeedback.proxy.OrderProxy;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import com.compassuol.sp.challenge.msfeedback.response.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.service.FeedbackService;
import com.compassuol.sp.challenge.msfeedback.service.assembler.FeedbackDtoAssembler;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
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
        if (responseFeedBack.isEmpty()) throw new BusinessException("No feedback was found.");

        List<FeedbackResponseDto> feedBackList = new ArrayList<>();

        responseFeedBack.forEach(feedback -> {
            feedBackList.add(assembler.toDto(feedback));
        });
        return feedBackList;
    }

    @Override
    public FeedbackResponseDto createFeedback(FeedbackRequestDto feedBackRequestDto) {
        OrderResponseDTO orderByIdResponse = null;
        try {
            orderByIdResponse = proxy.getOrderById(feedBackRequestDto.getOrderId());
        } catch (FeignException ex) {
            throw new BusinessException("No order was found for the order_id provided");
        }

        if (orderByIdResponse.getStatus().equals("CANCELED"))
            throw new BusinessException("It is not allowed to leave feedback on orders with status CANCELED");

        try {
            Scales.valueOf(feedBackRequestDto.getScale());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("The allowed satisfaction levels (scale) are: VERY_DISSATISFIED, DISSATISFIED, NEUTRAL, SATISFIED, VERY_ SATISFIED");
        }

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
        if (id <= 0) throw new InvalidDataException("Id value must be not null and greater than zero", "id");
        var feedbackOptional = repository.findById(id);
        if (feedbackOptional.isEmpty()) throw new BusinessException("No feedback was found fot the id passed");
        repository.deleteById(id);
    }

    @Override
    public FeedbackResponseDto getFeedbackById(long feedbackId) {
        var feedBackFound = repository
                .findById(feedbackId)
                .orElseThrow(EntityNotFoundException::new);

        return assembler.toDto(feedBackFound);
    }
}