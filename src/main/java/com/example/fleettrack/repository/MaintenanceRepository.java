package com.example.fleettrack.repository;

import com.example.fleettrack.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    void deleteAllByServiceDateBefore(LocalDate localDate);

    List<Maintenance> findAllByNextMaintenanceDateLessThanEqual(LocalDate now);

    List<Maintenance> findAllByVehicleId(Long vehicleId);
}
