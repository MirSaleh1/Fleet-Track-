package com.example.fleettrack.model.response;

import com.example.fleettrack.model.CarStatus;

public record VehicleResponse(
        Long id,
        String make,
        String model,
        Integer manufactureYear,
        String licensePlate,
        CarStatus carStatus
) {}