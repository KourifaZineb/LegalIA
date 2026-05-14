package com.chatbot.commonlibrary.exception;

import com.chatbot.commonlibrary.dtos.responses.ErrorResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("404")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Not found exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("400")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Bad request exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("401")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Unauthorized exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("403")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Forbidden exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("409")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Conflict exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternal(InternalServerErrorException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("500")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.error("Internal server error exception: {}", ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("422")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Validation exception: {}", ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(LawyerNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleLawyerNotAvailable(
            LawyerNotAvailableException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("400")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Lawyer not available: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConnectionAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleConnectionAlreadyExists(
            ConnectionAlreadyExistsException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("409")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Connection already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ConnectionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConnectionNotFound(
            ConnectionNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("404")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Connection not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidConnectionStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidConnectionStatus(
            InvalidConnectionStatusException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("400")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.warn("Invalid connection status: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(LawyerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLawyerNotFound(
            LawyerNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("404")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.error("Lawyer not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("404")
                .returnMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        log.error("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .returnCode("500")
                .returnMessage("Une erreur inattendue s'est produite")
                .timestamp(LocalDateTime.now())
                .build();

        log.error("Runtime exception: ", ex);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    // Removed duplicate Exception handler - RuntimeException already handles general exceptions
}