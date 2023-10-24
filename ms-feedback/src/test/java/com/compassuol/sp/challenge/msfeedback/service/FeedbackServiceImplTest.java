package com.compassuol.sp.challenge.msfeedback.service;

import static com.compassuol.sp.challenge.msfeedback.commons.FeedbacksConstants.*;

import com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msfeedback.model.dto.FeedbackResponseDto;
import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import com.compassuol.sp.challenge.msfeedback.proxy.OrderProxy;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import com.compassuol.sp.challenge.msfeedback.response.Address;
import com.compassuol.sp.challenge.msfeedback.response.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.response.ProductOrderDTO;
import com.compassuol.sp.challenge.msfeedback.service.assembler.FeedbackDtoAssembler;
import com.compassuol.sp.challenge.msfeedback.service.impl.FeedbackServiceImpl;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {
    @InjectMocks
    private FeedbackServiceImpl feedbackService;
    @Mock
    private FeedbackRepository repository;
    @Mock
    private OrderProxy proxy;
    @Mock
    OrderResponseDTO orderResponseDTO;
    @Mock
    FeedbackDtoAssembler assembler;


    @Test
    public void createFeedback_withValidData_ReturnsFeedback() {
        Feedback feedbackEntity = new Feedback();
        feedbackEntity.setComment(FEEDBACK.getComment());
        feedbackEntity.setId(FEEDBACK.getId());
        feedbackEntity.setScale(FEEDBACK.getScale());
        feedbackEntity.setOrderId(FEEDBACK.getOrderId());

        when(assembler.toDto(feedbackEntity)).thenReturn(FEEDBACK_RESPONSE_DTO);
        when(assembler.toModel(FEEDBACK_REQUEST_DTO)).thenReturn(feedbackEntity);
        when(proxy.getOrderById(1L)).thenReturn(ORDER_RESPONSE_DTO);
        when(repository.save(feedbackEntity)).thenReturn(feedbackEntity);

        FeedbackResponseDto sut = feedbackService.createFeedback(FEEDBACK_REQUEST_DTO);

        assertThat(sut).isEqualTo(FEEDBACK_RESPONSE_DTO);

        assertThat(sut.getScale()).isEqualTo(FEEDBACK_RESPONSE_DTO.getScale());
        assertThat(sut.getId()).isEqualTo(FEEDBACK_RESPONSE_DTO.getId());
        assertThat(sut.getOrderId()).isEqualTo(FEEDBACK_RESPONSE_DTO.getOrderId());
        assertThat(sut.getComment()).isEqualTo(FEEDBACK_RESPONSE_DTO.getComment());
    }

    @Test
    public void createFeedback_withInvalidOrderId_ThrowsException() {
        when(proxy.getOrderById(1L)).thenThrow(FeignException.class);

        try {
            feedbackService.createFeedback(FEEDBACK_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("No order was found for the order_id provided").isEqualTo(e.getMessage());
        }

    }

    @Test
    public void createFeedback_withInvalidFeedbackScale_ThrowsException() {
        Address address = new Address();
        address.setStreet(ADDRESS.getStreet());
        address.setState(ADDRESS.getState());
        address.setNumber(ADDRESS.getNumber());
        address.setCity(ADDRESS.getCity());
        address.setComplement(ADDRESS.getComplement());
        address.setPostalCode(ADDRESS.getPostalCode());

        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setProductId(PRODUCT_ORDER_DTO.getProductId());
        productOrderDTO.setQuantity(PRODUCT_ORDER_DTO.getQuantity());

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(ORDER_RESPONSE_DTO.getId());
        orderResponseDTO.setProducts(List.of(productOrderDTO));
        orderResponseDTO.setAddress(address);
        orderResponseDTO.setPaymentMethod(ORDER_RESPONSE_DTO.getPaymentMethod());
        orderResponseDTO.setSubtotalValue(ORDER_RESPONSE_DTO.getSubtotalValue());
        orderResponseDTO.setCreatedDate(ORDER_RESPONSE_DTO.getCreatedDate());
        orderResponseDTO.setDiscount(ORDER_RESPONSE_DTO.getDiscount());
        orderResponseDTO.setTotalValue(ORDER_RESPONSE_DTO.getTotalValue());
        orderResponseDTO.setStatus(ORDER_RESPONSE_DTO.getStatus());
        orderResponseDTO.setUpdateDate(ORDER_RESPONSE_DTO.getUpdateDate());
        orderResponseDTO.setCancelDate(ORDER_RESPONSE_DTO.getCancelDate());
        orderResponseDTO.setCancelReason(ORDER_RESPONSE_DTO.getCancelReason());

        when(proxy.getOrderById(1L)).thenReturn(orderResponseDTO);

        try {
            feedbackService.createFeedback(FEEDBACK_REQUEST_DTO_INVALID_SCALE);
        } catch (BusinessException e) {
            assertThat("The allowed satisfaction levels (scale) are: VERY_DISSATISFIED, DISSATISFIED, NEUTRAL, SATISFIED, VERY_ SATISFIED").isEqualTo(e.getMessage());
        }
    }

    @Test
    public void createFeedback_withInvalidOrderStatus_ThrowsException() {

        when(proxy.getOrderById(1L)).thenReturn(ORDER_RESPONSE_DTO_INVALID_STATUS);

        try {
            feedbackService.createFeedback(FEEDBACK_REQUEST_DTO);
        } catch (BusinessException e) {
            assertThat("It is not allowed to leave feedback on orders with status CANCELED").isEqualTo(e.getMessage());
        }
    }

    @Test
    void getAllFeedbacks_ReturnsListOfFeedback() {
        when(repository.findAll()).thenReturn(List.of(FEEDBACK));
        when(assembler.toDto(FEEDBACK)).thenReturn(FEEDBACK_RESPONSE_DTO);

        List<FeedbackResponseDto> sut = feedbackService.getAllFeedbacks();
    }

    @Test
    void getAllFeedbacks_ReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        try {
            feedbackService.getAllFeedbacks();
        } catch (BusinessException ex) {
            assertThat("No feedback was found.").isEqualTo(ex.getMessage());
        }
    }

    @Test
    public void deleteFeedbackById_withValidId_DeletesFeedback() {
        long feedbackId = 1L;
        when(repository.findById(feedbackId)).thenReturn(Optional.of(FEEDBACK));

        feedbackService.deleteFeedbackById(feedbackId);

        verify(repository, times(1)).deleteById(feedbackId);
    }

    @Test
    public void deleteFeedbackById_withInvalidId_ThrowsException() {
        long invalidId = 0L;

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            feedbackService.deleteFeedbackById(invalidId);
        });

        assertThat(exception.getMessage()).isEqualTo("Id value must be not null and greater than zero");

        verify(repository, never()).deleteById(invalidId);
    }

    @Test
    public void getFeedbackById_WithValidId_ReturnsProduct() {
        var productTest = FEEDBACK;
        when(repository.findById(1L))
                .thenReturn(Optional.of(productTest));

        var systemUnderTest = feedbackService
                .getFeedbackById(1L);

        assertThat(assembler.toDto(productTest))
                .isEqualTo(systemUnderTest);
    }

    @Test
    public void getFeedbackById_WithInvalidId_Throwable() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        var systemUnderTest = assertThrows(EntityNotFoundException.class,
                () -> feedbackService
                        .getFeedbackById(1L));

        assertThat(systemUnderTest.getClass()).isEqualTo(EntityNotFoundException.class);
    }

    @Test
    public void updateFeedback_WithValidData_ReturnsFeedback(){
        var feedbackId = 1L;

        when(repository.findById(feedbackId)).thenReturn(Optional.of(FEEDBACK));
        when(repository.save(any(Feedback.class))).thenAnswer(i -> i.getArguments()[0]);
        when(assembler.toDto(any(Feedback.class))).thenAnswer(i -> new FeedbackResponseDto());

        var response = feedbackService.updateFeedback(feedbackId, FEEDBACK_REQUEST_DTO);

        assertNotNull(response);
        verify(repository).findById(feedbackId);
        verify(repository).save(any(Feedback.class));
    }
}
