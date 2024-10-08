package com.compassuol.sp.challenge.msordes.Commons;

import com.compassuol.sp.challenge.msordes.enums.PaymentMethod;
import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.model.dto.*;
import com.compassuol.sp.challenge.msordes.model.entity.Order;
import com.compassuol.sp.challenge.msordes.model.entity.ProductOrder;
import com.compassuol.sp.challenge.msordes.response.AddressViaCep;
import com.compassuol.sp.challenge.msordes.response.Products;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class OrdersConstants {
    public static final Products PRODUCTS_RESPONSE = new Products(1L, "Product name", 10.0, "Product Description");
    public static final ProductOrderDTO PRODUCT_ORDER_DTO = new ProductOrderDTO(1L, 5);
    public static final ProductOrder PRODUCT = new ProductOrder(1L, 1L, 5, new Order());
    public static final Address ADDRESS = new Address("Street Name", 10, "", "Petrolina", "PE", "56332016");
    public static  final AddressViaCep ADDRESS_VIA_CEP = new AddressViaCep("56332016", "Rua 9", "", "Henrique Leite", "Petrolina", "PE", "", "", "", "");
    public static final Order ORDER = new Order(1L, List.of(PRODUCT), ADDRESS, PaymentMethod.PIX, 889.98, 0.05, 845.48, OffsetDateTime.now(), Status.CONFIRMED);
    public static final Order ORDER_INVALID = new Order(1L, null, null, PaymentMethod.PIX, 889.98, 0.05, 845.48, OffsetDateTime.now(), Status.CONFIRMED);
    public static final OrderRequestDTO ORDER_REQUEST_DTO = new OrderRequestDTO(List.of(PRODUCT_ORDER_DTO), ADDRESS, "PIX");
    public static final UpdateOrderRequestDTO UPDATE_ORDER_REQUEST_DTO = new UpdateOrderRequestDTO(1L, List.of(PRODUCT_ORDER_DTO), ADDRESS, "PIX", "SENT");
    public static final UpdateOrderRequestDTO UPDATE_ORDER_REQUEST_DTO_INVALID_STATUS = new UpdateOrderRequestDTO(1L, List.of(PRODUCT_ORDER_DTO), ADDRESS, "PIX", "TESTE");
    public static final UpdateOrderRequestDTO UPDATE_ORDER_REQUEST_DTO_INVALID_STATUS_CANCELED = new UpdateOrderRequestDTO(1L, List.of(PRODUCT_ORDER_DTO), ADDRESS, "PIX", "CANCELED");
    public static final OrderResponseDTO ORDER_RESPONSE_DTO =  new OrderResponseDTO(1L, List.of(PRODUCT_ORDER_DTO), ADDRESS, PaymentMethod.PIX, 889.98, 0.05, 845.48, OffsetDateTime.now(), Status.CONFIRMED);

    public static final CancelOrderRequestDTO CANCEL_ORDER_REQUEST_DTO = new CancelOrderRequestDTO("Este é o motivo");
    public static final Order ORDER_INVALID_DAYS = new Order(1L, List.of(PRODUCT), ADDRESS, PaymentMethod.PIX, 889.98, 0.05, 845.48, OffsetDateTime.now().minusDays(91), Status.CONFIRMED);
    public static final Order ORDER_INVALID_STATUS_SENT = new Order(1L, List.of(PRODUCT), ADDRESS, PaymentMethod.PIX, 889.98, 0.05, 845.48, OffsetDateTime.now(), Status.SENT);
    public static final Order ORDER_INVALID_STATUS_CANCELED = new Order(1L, List.of(PRODUCT), ADDRESS, PaymentMethod.PIX, 889.98, 0.05, 845.48, OffsetDateTime.now(), Status.CANCELED);

    public static final OrderResponseDTO ORDER_RESPONSE_DTO_CANCELED =  new OrderResponseDTO(1L, List.of(PRODUCT_ORDER_DTO),
            ADDRESS, PaymentMethod.PIX, 889.98, 0.05, 845.48, OffsetDateTime.now(), Status.CANCELED, null, OffsetDateTime.now(), "Este é o motivo");
}
