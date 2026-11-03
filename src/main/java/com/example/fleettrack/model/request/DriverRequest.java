package com.example.fleettrack.model.request;

public record DriverRequest(
        String firstName,
        String lastName,
        String licenseNumber,
        String phoneNumber
        ) {}