package com.example.fleettrack.service;

import com.example.fleettrack.entity.Maintenance;
import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.exception.BusinessException;
import com.example.fleettrack.exception.ExceptionConstants;
import com.example.fleettrack.mapper.MaintenanceMapper;
import com.example.fleettrack.model.CarStatus;
import com.example.fleettrack.model.request.MaintenanceRequest;
import com.example.fleettrack.model.response.MaintenanceResponse;
import com.example.fleettrack.repository.MaintenanceRepository;
import com.example.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final VehicleRepository vehicleRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final MaintenanceRepository maintenanceRepository;

    @Transactional
    @CacheEvict(value = "vehicle", key = "#request.licensePlate()")
    public MaintenanceResponse createMaintenance(MaintenanceRequest request) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(request.licensePlate())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.VEHICLE_NOT_FOUND));
        vehicle.setCarStatus(CarStatus.MAINTENANCE);
        vehicleRepository.save(vehicle);

        Maintenance maintenance = Maintenance.builder()
                .vehicle(vehicle)
                .description(request.description())
                .cost(request.cost())
                .serviceDate(LocalDate.now())
                .nextMaintenanceDate(LocalDate.now().plusMonths(6))
                .build();
        return maintenanceMapper.toResponse(maintenanceRepository.save(maintenance));
    }

    @Transactional
    @CacheEvict(value = "vehicle", key = "#licensePlate")
    public void completeMaintenance(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.VEHICLE_NOT_FOUND));
        vehicle.setCarStatus(CarStatus.AVAILABLE);
        vehicleRepository.save(vehicle);
    }

    public List<MaintenanceResponse> getAllMaintenances() {
        return maintenanceRepository.findAll()
                .stream()
                .map(maintenanceMapper::toResponse)
                .toList();
    }

    public List<MaintenanceResponse> getMaintenancesByVehicle(Long vehicleId) {
        return maintenanceRepository.findAllByVehicleId(vehicleId)
                .stream()
                .map(maintenanceMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public byte[] generatePdf() throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setLeading(18f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("FLEETTRACK MAINTENANCE REPORT");
                contentStream.newLine();
                for (Maintenance m : maintenanceRepository.findAll()) {
                    String line = m.getVehicle().getLicensePlate() + " | " + m.getDescription() + " | " + m.getCost();
                    contentStream.showText(line);
                    contentStream.newLine();
                }
                contentStream.endText();
            }
            document.save(out);
            return out.toByteArray();
        }
    }
}
