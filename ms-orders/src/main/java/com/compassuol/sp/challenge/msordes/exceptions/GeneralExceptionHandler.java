package com.compassuol.sp.challenge.msordes.exceptions;

import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InternalServerErrorException;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InvalidDataException;
import com.compassuol.sp.challenge.msordes.jacoco.ExcludeFromJacocoGeneratedReport;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ProductErrorResponse> handleInvalidDataException(InvalidDataException ex) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setStatus(HttpStatus.BAD_REQUEST.name());
        error.setMessage(ex.getMessage());
        error.setDetails(new DetailsProductErrorResponse(ex.getField(), ex.getMessage()));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProductErrorResponse> handleBusinessException(BusinessException ex) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setStatus(HttpStatus.BAD_REQUEST.name());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ProductErrorResponse> InternalServerErrorException(InternalServerErrorException ex) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setStatus(HttpStatus.BAD_REQUEST.name());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExcludeFromJacocoGeneratedReport
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProductErrorResponse> handleEntityNotFoundException(){
        var httpStatus = HttpStatus.NOT_FOUND;
        var productErrorResponse = new ProductErrorResponse();

        productErrorResponse.setCode(httpStatus.value());
        productErrorResponse.setStatus(httpStatus.name());
        productErrorResponse.setMessage("Product not found!");

        return new ResponseEntity<>(productErrorResponse, httpStatus);
    }
    @ExcludeFromJacocoGeneratedReport
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProductErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setCode(HttpStatus.CONFLICT.value());
        error.setStatus(HttpStatus.CONFLICT.name());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    @ExcludeFromJacocoGeneratedReport
    public ResponseEntity<ProductErrorResponse> getAllEntityNotFoundException(){
        var httpStatus = HttpStatus.NOT_FOUND;
        var productErrorResponse = new ProductErrorResponse();

        productErrorResponse.setCode(httpStatus.value());
        productErrorResponse.setStatus(httpStatus.name());
        productErrorResponse.setMessage("products not found!");

        return new ResponseEntity<>(productErrorResponse, httpStatus);
    }
}
