package com.fedag.CSR.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class HttpClientErrorExceptionCustom extends HttpStatusCodeException {

    public HttpClientErrorExceptionCustom(String message){
        super(HttpStatus.valueOf(message));
    }
}
