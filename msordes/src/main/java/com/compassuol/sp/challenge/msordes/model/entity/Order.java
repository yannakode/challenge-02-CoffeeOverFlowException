package com.compassuol.sp.challenge.msordes.model.entity;

import com.compassuol.sp.challenge.msordes.enums.PaymentMethod;
import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.model.dto.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "order_tb")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> products = new ArrayList<>();

    @Embedded
    private Address address;
    @Column(nullable = false)
    private PaymentMethod paymentMethod;
    @Column(nullable = false)
    private Double subtotalValue;
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double discount;
    @Column(nullable = false)
    private Double totalValue;
    @Column(nullable = false)
    private Date createdDate;
    @Column(nullable = false)
    private Status status;
}
