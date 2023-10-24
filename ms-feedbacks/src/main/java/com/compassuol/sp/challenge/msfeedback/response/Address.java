package com.compassuol.sp.challenge.msfeedback.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    private String street;
    private Integer number;
    private String complement;
    private String city;
    private String state;
    private String postalCode;
}

