package com.task.starwars.exception;

public class DtoBadRequestException extends RuntimeException{
    public DtoBadRequestException(String message) {
        super(message);
    }
}
