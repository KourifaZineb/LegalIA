package com.chatbot.commonlibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LawyerNotAvailableException extends RuntimeException {
    public LawyerNotAvailableException(String message) {
        super(message);
    }
}
