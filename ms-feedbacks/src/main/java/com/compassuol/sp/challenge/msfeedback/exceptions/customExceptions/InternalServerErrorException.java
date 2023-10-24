package com.compassuol.sp.challenge.msfeedback.exceptions.customExceptions;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
