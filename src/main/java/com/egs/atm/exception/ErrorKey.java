package com.egs.atm.exception;

import org.springframework.http.HttpStatus;

public enum ErrorKey {

    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private HttpStatus status;

    ErrorKey(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
