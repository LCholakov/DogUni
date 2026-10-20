package com.doguni.exception;

public class EmailAlreadyExistsException extends DogUniException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}