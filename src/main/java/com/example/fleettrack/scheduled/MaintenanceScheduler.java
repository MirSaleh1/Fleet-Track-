package com.example.fleettrack.scheduled;

import com.example.fleettrack.entity.Maintenance;
import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.model.CarStatus;
import com.example.fleettrack.repository.MaintenanceRepository;
import com.example.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaintenanceScheduler {

    private final MaintenanceRepository maintenanceRepository;
    private final StringRedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 9 * * *")
    @Transactional
    public void checkUpcomingMaintenances() {
        List<Maintenance> dueList = maintenanceRepository
                .findAllByNextMaintenanceDateLessThanEqual(LocalDate.now());
        for (Maintenance m : dueList) {
            String message = "Diqqət! " + m.getVehicle().getLicensePlate()
                    + " nömrəli maşının texniki baxış vaxtı çatıb! Son tarix: " + m.getNextMaintenanceDate();
            redisTemplate.convertAndSend("maintenance-alerts", message);
        }
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void cleanUpOutdatedRecords() {
        maintenanceRepository.deleteAllByServiceDateBefore(LocalDate.now().minusYears(1));
        log.info("Köhnə təmir tarixçələri təmizləndi.");
    }
}