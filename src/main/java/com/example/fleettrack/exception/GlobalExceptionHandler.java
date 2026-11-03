package com.example.fleettrack.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ExceptionResponse handleBusinessException(BusinessException ex, HttpServletResponse response) {
        response.setStatus(ex.getExceptionConstants().getStatus().value());
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ExceptionResponse(errors);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ExceptionResponse handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return new ExceptionResponse("Sizin bu emeliyyati yerine yetirmek ucun icazeniz yoxdur! (Access Denied)");
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleGeneralException(Exception ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ExceptionResponse("Daxili server xətası baş verdi: " + ex.getMessage());
    }
}
