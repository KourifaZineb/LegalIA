package com.chatbot.commonlibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConnectionAlreadyExistsException extends RuntimeException {
  public ConnectionAlreadyExistsException(String message) {
    super(message);
  }
}
