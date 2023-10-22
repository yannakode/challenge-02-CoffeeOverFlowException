package com.compassuol.sp.challenge.msordes.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Products {
    private Long id;
    private String name;
    private Double value;
    private String description;
}
