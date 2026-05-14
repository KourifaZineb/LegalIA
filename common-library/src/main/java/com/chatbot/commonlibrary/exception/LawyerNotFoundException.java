package com.chatbot.commonlibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LawyerNotFoundException extends RuntimeException {
    public LawyerNotFoundException(String message) {
        super(message);
    }
}
