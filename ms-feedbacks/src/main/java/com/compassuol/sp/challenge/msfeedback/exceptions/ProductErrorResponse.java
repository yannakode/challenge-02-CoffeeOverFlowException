package com.compassuol.sp.challenge.msfeedback.exceptions;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class ProductErrorResponse {
    private Integer code;
    private String status;
    private String message;
    private DetailsProductErrorResponse details;

}
@Getter
@Setter
class DetailsProductErrorResponse {
    private String fields;
    private String message;
    public DetailsProductErrorResponse(String fields, String message) {
        this.fields = fields;
        this.message = message;
    }
}
