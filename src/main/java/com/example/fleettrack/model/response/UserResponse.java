package com.example.fleettrack.model.response;


import java.util.List;

public record UserResponse(
    String name,
    String email,
    List<AuthorityResponse> authorities
) {}
