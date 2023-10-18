package com.compassuol.sp.challenge.msordes.exceptions;


import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.BusinessException;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InternalServerErrorException;
import com.compassuol.sp.challenge.msordes.exceptions.customExceptions.InvalidDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GeneralExceptionHandlerTest {
    @InjectMocks
    private GeneralExceptionHandler exceptionHandler;

    @Test
    public void testHandleInvalidDataException() {
        InvalidDataException ex = new InvalidDataException("Invalid data", "fieldName");
        ex.setField("Teste");

        ResponseEntity<ProductErrorResponse> response = exceptionHandler.handleInvalidDataException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid data", response.getBody().getMessage());
        assertEquals("BAD_REQUEST", response.getBody().getStatus());
        assertEquals(400, response.getBody().getCode());
    }
    @Test
    public void testHandleBusinessException() {
        BusinessException ex = new BusinessException("Invalid data");

        ResponseEntity<ProductErrorResponse> response = exceptionHandler.handleBusinessException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid data", response.getBody().getMessage());
    }

    @Test
    public void testInternalServerErrorException() {
        InternalServerErrorException ex = new InternalServerErrorException("Invalid data");

        ResponseEntity<ProductErrorResponse> response = exceptionHandler.InternalServerErrorException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid data", response.getBody().getMessage());
    }
}
