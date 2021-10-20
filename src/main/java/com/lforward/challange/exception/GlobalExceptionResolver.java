package com.lforward.challange.exception;

import com.lforward.challange.model.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author bashir
 * @since 19/10/21.
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionResolver {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> bindException(BindException ex, Errors errors) {
        String errorMsg = errors.getFieldErrors().stream()
                .map(fe -> fe.getField() + " " + fe.getDefaultMessage())
                .collect(Collectors.joining(";"));

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorMsg);

        log.debug("ApiError: apiError: {}", apiError);
        log.debug("Exception ", ex);

        return createErrorResponse(apiError);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ApiError> entityNotFoundException(RecordNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        log.debug("ApiError: apiError: {}", apiError);
        log.debug("Exception ", ex);

        return createErrorResponse(apiError);
    }

    private ResponseEntity<ApiError> createErrorResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.status());
    }
}
