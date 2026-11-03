package com.example.fleettrack.controller;

import com.example.fleettrack.model.request.LoginRequest;
import com.example.fleettrack.model.request.RegisterRequest;
import com.example.fleettrack.model.request.TokenRequest;
import com.example.fleettrack.model.response.TokenResponse;
import com.example.fleettrack.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody TokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}
