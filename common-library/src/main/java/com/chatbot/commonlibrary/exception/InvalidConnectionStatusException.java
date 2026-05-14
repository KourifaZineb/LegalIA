package com.chatbot.commonlibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidConnectionStatusException extends RuntimeException {
    public InvalidConnectionStatusException(String message) {
        super(message);
    }
}
