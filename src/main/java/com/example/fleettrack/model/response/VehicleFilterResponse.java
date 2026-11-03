package com.example.fleettrack.model.response;

import com.example.fleettrack.entity.Vehicle;

import java.util.List;

public record VehicleFilterResponse(List<VehicleResponse> vehicleResponses,
                                    long totalElements,
                                    int totalPages,
                                    int currentPage,
                                    int pageSize,
                                    boolean hasNext) {
}
