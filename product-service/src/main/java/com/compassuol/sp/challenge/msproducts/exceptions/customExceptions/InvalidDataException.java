package com.compassuol.sp.challenge.msproducts.exceptions.customExceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidDataException extends RuntimeException{

    private String fields;
    public InvalidDataException(String message, String fields) {
        super(message);
        this.fields = fields;
    }
}
