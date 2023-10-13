package com.compassuol.sp.challenge.msordes.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor

@Getter
@Setter
@ToString
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
