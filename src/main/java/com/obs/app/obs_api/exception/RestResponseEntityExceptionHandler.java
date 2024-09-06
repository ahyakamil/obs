package com.obs.app.obs_api.exception;

import com.obs.app.obs_api.common.ResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ConflictException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleConflict(ConflictException e) {
        return new ResponseWrapper<>().buildConflictException(e);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleGeneral(Exception e) {
        return new ResponseWrapper<>().buildGeneralException(e);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleNotFound(NotFoundException e) {
        return new ResponseWrapper<>().buildNotFoundException(e);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<ResponseWrapper<Object>> handleBadRequest(BadRequestException e) {
        return new ResponseWrapper<>().buildBadRequestException(e);
    }
}
