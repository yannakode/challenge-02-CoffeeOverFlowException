package com.compassuol.sp.challenge.msordes.model.entity;

import com.compassuol.sp.challenge.msordes.enums.PaymentMethod;
import com.compassuol.sp.challenge.msordes.enums.Status;
import com.compassuol.sp.challenge.msordes.model.dto.Address;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "order_tb")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @Column(name = "date", nullable = false, columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime updateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime cancelDate;
    private String cancelReason;
    @Column(nullable = false)
    private Status status;

    public Order(Long id, List<ProductOrder> products, Address address, PaymentMethod paymentMethod, Double subtotalValue, Double discount, Double totalValue, OffsetDateTime createdDate, Status status) {
        this.id = id;
        this.products = products;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.subtotalValue = subtotalValue;
        this.discount = discount;
        this.totalValue = totalValue;
        this.createdDate = createdDate;
        this.status = status;
    }
}
