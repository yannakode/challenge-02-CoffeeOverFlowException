package com.compassuol.sp.challenge.msordes.exceptions.customExceptions;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
