package com.example.fleettrack.model;

public record LocationUpdateRequest(Long vehicleId,
                                    Double latitude,
                                    Double longitude  ) {
}
