package com.compassuol.sp.challenge.msordes.repository;

import static com.compassuol.sp.challenge.msordes.Commons.OrdersConstants.*;

import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;


@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Mock
    private OrderRepository repository;

    @Test
    public void getAllOrders_WithValidStatus_ReturnsOrderList() {
        when(repository.findByStatus(Status.CONFIRMED)).thenReturn(Arrays.asList(new Order(), new Order()));

        List<Order> orderList = repository.findByStatus(Status.CONFIRMED);

        assertThat(orderList).isNotEmpty();
        assertThat(orderList).hasSize(2);
    }
    @Test
    public void createOrder_WithValidData_ReturnsOrder() {
        when(repository.save(ORDER)).thenReturn(ORDER);
        Order sut = repository.save(ORDER);
        assertThat(sut).isEqualTo(ORDER);
    }

    @Test
    public void createProduct_WithInvalidData_ThrowsException() {
        when(repository.save(ORDER_INVALID)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> repository.save(ORDER_INVALID));
    }
}
