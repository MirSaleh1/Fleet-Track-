package com.example.fleettrack.repository;

import com.example.fleettrack.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle>{

    boolean existsByLicensePlate(String licensePlate);

    Optional<Vehicle> findByLicensePlate(String licensePlate);










}
