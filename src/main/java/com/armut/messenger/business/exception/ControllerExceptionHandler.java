package com.armut.messenger.business.exception;

import com.armut.messenger.presentation.api.dto.APIErrorResponseDTO;
import com.armut.messenger.presentation.api.dto.APIResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    //TODO: ex.getMessage() ile gelen hatayı mutlaka logla. Aşağıdaki metotta hatayı kullanıcıya gösterme.
    @ExceptionHandler(value = { Throwable.class, Exception.class })
    protected ResponseEntity<Object> throwable(
            Exception ex, WebRequest request) {
        APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(HttpStatus.BAD_GATEWAY,ex.getMessage());
        return handleExceptionInternal(ex, apiErrorResponse,
                new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
    }

    @ExceptionHandler(value = { APIException.class })
    protected ResponseEntity<Object> handleConflict(
            APIException ex, WebRequest request) {
        APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(ex.getHttpStatus(),ex.getMessage());
        return handleExceptionInternal(ex, apiErrorResponse,
                new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        //return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        //TODO: LOG yazılacak, log ile gerçek hatayı bastır.
        return handleExceptionInternal(ex, "REQUEST FORMAT INVALID",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

}
