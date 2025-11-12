package com.carproduction.demo.demo.exceptions;

public class CarNotExistsException extends RuntimeException {
    

    public CarNotExistsException(String message){
        super(message);
    }

    public CarNotExistsException(String message, Throwable throwable ){
        super(message, throwable);
    }
}
