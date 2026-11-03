package com.example.fleettrack.model.request;

public record UpdateProfileRequest(
    String name,
    String password
) {}
