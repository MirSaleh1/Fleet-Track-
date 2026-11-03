package com.example.fleettrack.model.request;

public record VehicleRequest(
        String make,
        String model,
        Integer manufactureYear,
        String licensePlate
) {}