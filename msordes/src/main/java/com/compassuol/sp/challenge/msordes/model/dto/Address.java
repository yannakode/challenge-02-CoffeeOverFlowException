package com.compassuol.sp.challenge.msordes.model.dto;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@ToString
public class Address {
    private String street;
    private Integer number;
    private String complement;
    private String city;
    private String state;
    private String postalCode;

}

