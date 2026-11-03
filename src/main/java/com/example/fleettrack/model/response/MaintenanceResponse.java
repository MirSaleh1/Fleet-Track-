package com.example.fleettrack.model.response;

import java.time.LocalDate;

public record MaintenanceResponse(
        Long id,
        Long vehicleId,
        String description,
        Double cost,
        LocalDate serviceDate,
        LocalDate nextMaintenanceDate
) {}