package com.compassuol.sp.challenge.msproducts.exceptions.customExceptions;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
