package com.compassuol.sp.challenge.msproducts.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_tb")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;
    @DecimalMin(value = "0.01")
    private Double value;
    @Size(min = 10)
    private String description;

    public Product(String name, Double value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }
}
