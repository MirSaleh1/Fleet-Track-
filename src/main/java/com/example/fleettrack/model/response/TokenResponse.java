package com.example.fleettrack.model.response;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
}
