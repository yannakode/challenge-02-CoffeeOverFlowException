package com.compassuol.sp.challenge.msordes.exceptions;

import lombok.Data;

@Data
public class ProductErrorResponse {
    private Integer code;
    private String status;
    private String message;
    private DetailsProductErrorResponse details;

}
@Data
class DetailsProductErrorResponse {
    private String fields;
    private String message;
    public DetailsProductErrorResponse(String fields, String message) {
        this.fields = fields;
        this.message = message;
    }
}
