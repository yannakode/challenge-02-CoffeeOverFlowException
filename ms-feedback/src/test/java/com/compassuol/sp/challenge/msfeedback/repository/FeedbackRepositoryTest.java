package com.compassuol.sp.challenge.msfeedback.repository;

import static com.compassuol.sp.challenge.msfeedback.commons.FeedbacksConstants.*;

import com.compassuol.sp.challenge.msfeedback.model.entity.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackRepositoryTest {
    @Mock
    private FeedbackRepository repository;

    @Test
    public void createOrder_WithValidData_ReturnsOrder() {
        when(repository.save(FEEDBACK)).thenReturn(FEEDBACK);
        Feedback sut = repository.save(FEEDBACK);
        assertThat(sut).isEqualTo(FEEDBACK);
    }

    @Test
    public void getAllOrders_WithValidData_ReturnsOrder() {
        when(repository.findAll()).thenReturn(FEEDBACK_LIST);
        List<Feedback> sut = repository.findAll();
        assertThat(sut).isEqualTo(FEEDBACK_LIST);
    }
}
