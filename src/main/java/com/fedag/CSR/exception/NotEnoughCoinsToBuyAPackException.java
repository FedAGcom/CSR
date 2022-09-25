package com.fedag.CSR.exception;

public class NotEnoughCoinsToBuyAPackException extends RuntimeException {

    public NotEnoughCoinsToBuyAPackException(String message) {
        super(message);
    }
}
