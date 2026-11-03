package com.example.fleettrack.model.request;

import jakarta.validation.constraints.Min;

public record MaintenanceRequest(
        String licensePlate,
        String description,
        @Min(0)
        Double cost
) {}