package com.example.fleettrack.controller;

import com.example.fleettrack.model.request.DriverRequest;
import com.example.fleettrack.model.request.PagesRequest;
import com.example.fleettrack.model.response.DriverFilterResponse;
import com.example.fleettrack.model.response.DriverResponse;
import com.example.fleettrack.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<DriverResponse> createDriver(@Valid @RequestBody DriverRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.createDriver(request));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<DriverResponse> updateDriver(@Valid @RequestBody DriverRequest request) {
        return ResponseEntity.ok(driverService.updateDriver(request));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DriverResponse> deleteDriver(@Valid @RequestBody DriverRequest request) {
        return ResponseEntity.ok(driverService.deleteDriver(request));
    }

    @PostMapping("/{driverId}/assign-vehicle/{licensePlate}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<DriverResponse> assignVehicleToDriver(@PathVariable Long driverId,
                                                                @PathVariable String licensePlate) {
        return ResponseEntity.ok(driverService.assignVehicleToDriver(driverId, licensePlate));
    }

    @GetMapping("/{licenseNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<DriverResponse> getDriverByLicenseNumber(@PathVariable String licenseNumber) {
        return ResponseEntity.ok(driverService.getDriverByLicenseNumber(licenseNumber));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<DriverFilterResponse> getAllDrivers(@ModelAttribute DriverRequest filterRequest,
                                                              @Valid @ModelAttribute PagesRequest pagesRequest) {
        return ResponseEntity.ok(driverService.getAllVehicles(filterRequest, pagesRequest));
    }
}
