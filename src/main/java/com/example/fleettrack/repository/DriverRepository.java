package com.example.fleettrack.repository;

import com.example.fleettrack.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver,Long>, JpaSpecificationExecutor<Driver> {

    @EntityGraph(attributePaths = "assignedVehicles")
    Optional<Driver> findByLicenseNumber(String licenseNumber);
    @Override
    @EntityGraph(attributePaths = "assignedVehicles")
    Optional<Driver> findById(Long id);
    @Override
    @EntityGraph(attributePaths = "assignedVehicles")
    Page<Driver> findAll(Specification<Driver> spec, Pageable pageable);

}
