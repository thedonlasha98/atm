package com.egs.atm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EGSException extends RuntimeException {

    String message;

    HttpStatus status;

    public EGSException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public EGSException(ErrorKey error) {
        super(error.name());
        this.message = error.name();
        this.status = error.getStatus();
    }


}
