package com.egs.atm.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@Data
public class EGSResponse<T> {

    T body;

    HttpStatus status;

    public EGSResponse(T body, HttpStatus status) {
        new Response<>(body, status);
        this.body = body;
        this.status = status;
    }

    public EGSResponse(HttpStatus status) {
        new Response<>(status);
        this.body = (T) status;
        this.status = status;
    }

    class Response<T> extends ResponseEntity<T> {

        public Response(T body, HttpStatus status) {
            super(body, status);
        }

        public Response(HttpStatus status) {
            super(status);
        }
    }
}