package com.example.fleettrack.controller;

import com.example.fleettrack.model.request.MaintenanceRequest;
import com.example.fleettrack.model.response.MaintenanceResponse;
import com.example.fleettrack.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<MaintenanceResponse> createMaintenance(@Valid @RequestBody MaintenanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.createMaintenance(request));
    }

    @PatchMapping("/complete/{licensePlate}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<Void> completeMaintenance(@PathVariable String licensePlate) {
        maintenanceService.completeMaintenance(licensePlate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<List<MaintenanceResponse>> getAllMaintenances() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenances());
    }

    @GetMapping("/vehicle/{vehicleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<List<MaintenanceResponse>> getMaintenancesByVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(maintenanceService.getMaintenancesByVehicle(vehicleId));
    }

    @GetMapping("/report/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'FLEET_MANAGER')")
    public ResponseEntity<byte[]> downloadPdfReport() throws IOException {
        byte[] pdfBytes = maintenanceService.generatePdf();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=maintenance-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
