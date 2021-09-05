package com.armut.messenger.business.exception;

import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.model.User;
import com.armut.messenger.presentation.api.dto.APIErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Throwable.class, Exception.class})
    protected ResponseEntity<Object> throwable(
            Exception ex, WebRequest request) {
        APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_ERROR,
                HttpStatus.BAD_GATEWAY, "Something went wrong!");
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
    }

    @ExceptionHandler(value = {APIException.class})
    protected ResponseEntity<Object> handleConflict(
            APIException ex, WebRequest request) {
        APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_ERROR,
                ex.getHttpStatus(), ex.getMessage());

        User authUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER,0);
        if (Objects.isNull(authUser)){
            log.error(ex.getMessage() +"\n Request Data: " + ex.getRequestData());
        }
        else{
            log.error(ex.getMessage() + " - Auth UserID: " + authUser.getId() +"\n Request Data: " + ex.getRequestData());
        }
        return handleExceptionInternal(ex, apiErrorResponse, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_ERROR,
                HttpStatus.BAD_REQUEST, errors);

        User authUser = (User) request.getAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER,0);
        if (Objects.isNull(authUser)){
            log.error("Validation ERROR: " + ex.getMessage());
        }
        else{
            log.error("Validation ERROR: " + ex.getMessage() + " - Auth UserID: " + authUser.getId());
        }
        return handleExceptionInternal(ex, apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
