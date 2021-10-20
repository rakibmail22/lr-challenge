package com.lforward.challange.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

/**
 * @author bashir
 * @since 19/10/21.
 */
public record ApiError(
        HttpStatus status,
        @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy hh:mm:ss") LocalDateTime timestamp,
        String msg) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ApiError(HttpStatus status, String msg) {
        this(status, LocalDateTime.now(), msg);
    }
}