package com.compassuol.sp.challenge.msordes.repository;

import static com.compassuol.sp.challenge.msordes.Commons.OrdersConstants.*;

import com.compassuol.sp.challenge.msordes.model.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Mock
    private OrderRepository repository;

    @Test
    public void createOrder_WithValidData_ReturnsOrder() {
        when(repository.save(ORDER)).thenReturn(ORDER);
        Order sut = repository.save(ORDER);
        assertThat(sut).isEqualTo(ORDER);
    }

    @Test
    public void createOrder_WithInvalidData_ThrowsException() {
        when(repository.save(ORDER_INVALID)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> repository.save(ORDER_INVALID));
    }

    @Test
    public void getOrderById_WithValidData_ReturnsOrder() {
        when(repository.findById(1L)).thenReturn(Optional.of(ORDER));
        Optional<Order> sut = repository.findById(1L);
        assertThat(sut.get()).isEqualTo(ORDER);
    }

    @Test
    public void getOrderById_WithInvalidData_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Optional<Order> sut = repository.findById(1L);
        assertThat(sut.isEmpty()).isEqualTo(true);
    }

}
