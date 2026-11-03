package com.example.fleettrack.controller;

import com.example.fleettrack.model.request.PagesRequest;
import com.example.fleettrack.model.request.VehicleFilterRequest;
import com.example.fleettrack.model.request.VehicleRequest;
import com.example.fleettrack.model.response.VehicleFilterResponse;
import com.example.fleettrack.model.response.VehicleResponse;
import com.example.fleettrack.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<VehicleResponse> createVehicle(@RequestBody VehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(request));
    }

    @PutMapping("/{vehicleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable Long vehicleId,
                                                         @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleId, request));
    }

    @DeleteMapping("/{vehicleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VehicleResponse> deleteVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(vehicleService.deleteVehicle(vehicleId));
    }

    @GetMapping("/{licensePlate}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<VehicleResponse> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.ok(vehicleService.getVehicleByLicensePlate(licensePlate));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<VehicleFilterResponse> getAllVehicles(@ModelAttribute VehicleFilterRequest filterRequest,
                                                                @Valid @ModelAttribute PagesRequest pagesRequest) {
        return ResponseEntity.ok(vehicleService.getAllVehicles(filterRequest, pagesRequest));
    }
}
