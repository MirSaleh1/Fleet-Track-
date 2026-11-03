package com.example.fleettrack.service;

import com.example.fleettrack.entity.Driver;
import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.exception.BusinessException;
import com.example.fleettrack.exception.ExceptionConstants;
import com.example.fleettrack.mapper.DriverFilterResponseMapper;
import com.example.fleettrack.mapper.DriverResponseMapper;
import com.example.fleettrack.model.CarStatus;
import com.example.fleettrack.model.DriverSpecification;
import com.example.fleettrack.model.request.DriverRequest;
import com.example.fleettrack.model.request.PagesRequest;
import com.example.fleettrack.model.response.DriverFilterResponse;
import com.example.fleettrack.model.response.DriverResponse;
import com.example.fleettrack.repository.DriverRepository;
import com.example.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverResponseMapper driverResponseMapper;
    private final VehicleRepository vehicleRepository;
    private final DriverFilterResponseMapper driverFilterResponseMapper;

    @CachePut(value = "drivers", key = "#driverRequest.licenseNumber()")
    public DriverResponse createDriver(DriverRequest driverRequest) {
        if (driverRepository.findByLicenseNumber(driverRequest.licenseNumber()).isPresent()) {
            throw new BusinessException(ExceptionConstants.DRIVER_ALREADY_EXISTS);
        }
        Driver driver = Driver.builder()
                .firstName(driverRequest.firstName())
                .lastName(driverRequest.lastName())
                .licenseNumber(driverRequest.licenseNumber())
                .phoneNumber(driverRequest.phoneNumber())
                .build();
        return driverResponseMapper.maptoDriverResponse(driverRepository.save(driver));
    }

    @CachePut(value = "drivers", key = "#result.licenseNumber()")
    public DriverResponse updateDriver(DriverRequest driverRequest) {
        Driver driver = driverRepository.findByLicenseNumber(driverRequest.licenseNumber())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.DRIVER_NOT_FOUND));
        driverResponseMapper.uptadeDriver(driverRequest, driver);
        return driverResponseMapper.maptoDriverResponse(driverRepository.save(driver));
    }

    @CacheEvict(value = "drivers", key = "#result.licenseNumber()")
    public DriverResponse deleteDriver(DriverRequest driverRequest) {
        Driver driver = driverRepository.findByLicenseNumber(driverRequest.licenseNumber())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.DRIVER_NOT_FOUND));
        driverRepository.delete(driver);
        return driverResponseMapper.maptoDriverResponse(driver);
    }

    @Transactional
    @CachePut(value = "drivers", key = "#result.licenseNumber()")
    @CacheEvict(value = "vehicle", key = "#licensePlate")
    public DriverResponse assignVehicleToDriver(Long driverId, String licensePlate) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.DRIVER_NOT_FOUND));
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.VEHICLE_NOT_FOUND));
        if (!vehicle.getCarStatus().equals(CarStatus.AVAILABLE)) {
            throw new BusinessException(ExceptionConstants.VEHICLE_NOT_AVAILABLE);
        }
        vehicle.setDriver(driver);
        vehicle.setCarStatus(CarStatus.ON_ROUTE);
        driver.getAssignedVehicles().add(vehicleRepository.save(vehicle));
        return driverResponseMapper.maptoDriverResponse(driver);
    }

    @Cacheable(value = "drivers", key = "#licenseNumber")
    public DriverResponse getDriverByLicenseNumber(String licenseNumber) {
        Driver driver = driverRepository.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.DRIVER_NOT_FOUND));
        return driverResponseMapper.maptoDriverResponse(driver);
    }

    public DriverFilterResponse getAllVehicles(DriverRequest driverRequest, PagesRequest pagesRequest) {
        Specification<Driver> driverSpecification = DriverSpecification.toSpecification(driverRequest);
        Page<Driver> driverPages = driverRepository.findAll(driverSpecification, pagesRequest.toPageable());
        return driverFilterResponseMapper.maptoVehicleFilterResponse(driverPages);
    }
}
