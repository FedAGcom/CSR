package com.fedag.CSR.exception;

public class StatusHadBeenChangedException extends RuntimeException{
    public StatusHadBeenChangedException(String message) {
        super(message);
    }
}
