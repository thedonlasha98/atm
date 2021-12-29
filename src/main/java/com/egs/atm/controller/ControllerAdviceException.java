package com.egs.atm.controller;

import com.egs.atm.exception.EGSException;
import com.egs.atm.model.response.EGSExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(EGSException.class)
    ResponseEntity<EGSExceptionResponse> handleException(EGSException ex, HttpServletRequest req) {
        log.error("Request: " + req.getRequestURL() + " raised ", ex);
        return new ResponseEntity<>(new EGSExceptionResponse(ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    ResponseEntity<EGSExceptionResponse> handleException(HttpClientErrorException ex, HttpServletRequest req) {
        log.error("Request: " + req.getRequestURL() + " raised ", ex);
        return new ResponseEntity<>(new EGSExceptionResponse(ex.getMessage()), ex.getStatusCode());
    }

}
