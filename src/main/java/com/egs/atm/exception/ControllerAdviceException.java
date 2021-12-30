package com.egs.atm.exception;

import com.egs.atm.exception.EGSException;
import com.egs.atm.model.response.EGSExceptionResponse;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ControllerAdviceException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EGSException.class)
    ResponseEntity<Object> handleEGSException(EGSException ex, WebRequest req) {
        ex.printStackTrace();
        EGSExceptionResponse response = new EGSExceptionResponse(ex.getMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), ex.getStatus(), req);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest req) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), ex.getStatusCode(), req);
    }

    @ExceptionHandler(HystrixRuntimeException.class)
    protected ResponseEntity<Object> handleHystrixException(RuntimeException ex, WebRequest req) {
        ex.printStackTrace();
        EGSExceptionResponse response = new EGSExceptionResponse(ex.getMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, req);
    }
}
