package com.example.fleettrack.model.response;

import com.example.fleettrack.entity.Driver;

import java.util.List;

public record DriverFilterResponse(List<DriverResponse> drivers,
                                   long totalElements,
                                   int totalPages,
                                   int currentPage,
                                   int pageSize,
                                   boolean hasNext) {
}
