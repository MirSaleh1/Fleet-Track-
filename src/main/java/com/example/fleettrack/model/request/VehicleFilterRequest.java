package com.example.fleettrack.model.request;

import com.example.fleettrack.model.CarStatus;

public record VehicleFilterRequest(
        String make,
        String model,
        Integer manufactureYear,
        String licensePlate,
        CarStatus carStatus
) {}