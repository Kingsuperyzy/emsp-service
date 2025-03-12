package com.yzy.emsp.utils;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;

/**
 * REST API Response Wrapper (English Version)
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    // Response Status Code
    private int status;
    
    // Human-readable message
    private String message;
    
    // Response data (generic type)
    private T data;
    
    // Timestamp (ISO 8601 format)
    private String timestamp;

    // Private constructor for immutability
    private Result() {
        this.timestamp = Instant.now().toString();
    }

    // ------------------- Success Responses ------------------- //
    
    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .status(HttpStatus.OK.value())
                .message("Operation succeeded")
                .data(data);
    }

    public static <T> Result<T> created(T data) {
        return new Result<T>()
                .status(HttpStatus.CREATED.value())
                .message("Resource created successfully")
                .data(data);
    }

    // ------------------- Error Responses ------------------- //
    
    public static <T> Result<T> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static <T> Result<T> notFound(String message) {
        return error(HttpStatus.NOT_FOUND.value(), message);
    }

    public static <T> Result<T> serverError(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public static <T> Result<T> error(int status, String message) {
        return new Result<T>()
                .status(status)
                .message(message);
    }

    // ------------------- Fluent Setters ------------------- //
    
    private Result<T> status(int status) {
        this.status = status;
        return this;
    }

    private Result<T> message(String message) {
        this.message = message;
        return this;
    }

    private Result<T> data(T data) {
        this.data = data;
        return this;
    }

    // ------------------- Getters ------------------- //
    
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getTimestamp() {
        return timestamp;
    }
}