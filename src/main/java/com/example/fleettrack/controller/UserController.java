package com.example.fleettrack.controller;

import com.example.fleettrack.model.response.UserResponse;
import com.example.fleettrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/{userId}/roles/{authorityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> assignRoleToUser(@PathVariable Long userId,
                                                         @PathVariable Long authorityId) {
        return ResponseEntity.ok(userService.assignRoleToUser(userId, authorityId));
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> removeRoleFromUser(@PathVariable Long userId,
                                                           @PathVariable Long roleId) {
        return ResponseEntity.ok(userService.removeRoleFromUser(userId, roleId));
    }
}
