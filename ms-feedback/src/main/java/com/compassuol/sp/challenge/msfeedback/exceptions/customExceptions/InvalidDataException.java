package com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidDataException extends RuntimeException{

    private String field;
    public InvalidDataException(String message, String fields) {
        super(message);
        this.field = fields;
    }
}
