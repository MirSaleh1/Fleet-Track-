package com.example.fleettrack.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstants {
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("User already exists", HttpStatus.CONFLICT),
    USER_CANNOT_BE_NULL("User cannot be null", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN("Invalid refresh token", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXISTS("Role already exists", HttpStatus.CONFLICT),
    DRIVER_NOT_FOUND("Driver not found", HttpStatus.NOT_FOUND),
    DRIVER_ALREADY_EXISTS("Driver already exists", HttpStatus.CONFLICT),
    VEHICLE_NOT_FOUND("Vehicle not found", HttpStatus.NOT_FOUND),
    VEHICLE_ALREADY_EXISTS("Vehicle already exists", HttpStatus.CONFLICT),
    VEHICLE_NOT_AVAILABLE("Vehicle is not available", HttpStatus.BAD_REQUEST),
    MAINTENANCE_NOT_FOUND("Maintenance not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
