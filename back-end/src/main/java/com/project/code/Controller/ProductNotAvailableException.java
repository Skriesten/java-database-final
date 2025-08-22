package com.project.code.Controller;

public class ProductNotAvailableException extends Throwable {

    public ProductNotAvailableException( String message) {
        super(message);
    }
}
