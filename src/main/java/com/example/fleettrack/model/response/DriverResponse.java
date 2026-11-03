package com.example.fleettrack.model.response;

import java.util.List;

public record DriverResponse(
        Long id,
        String firstName,
        String lastName,
        String licenseNumber,
        String phoneNumber,
        List<VehicleResponse> vehicleResponseList
) {}