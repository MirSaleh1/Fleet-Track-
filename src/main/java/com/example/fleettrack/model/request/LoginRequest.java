package com.example.fleettrack.model.request;

public record LoginRequest(
    String email,
    String password
) {}
