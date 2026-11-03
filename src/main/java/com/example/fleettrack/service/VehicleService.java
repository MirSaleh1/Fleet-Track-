package com.example.fleettrack.service;

import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.exception.BusinessException;
import com.example.fleettrack.exception.ExceptionConstants;
import com.example.fleettrack.mapper.VehicleFilterResponseMapper;
import com.example.fleettrack.mapper.VehicleResponseMapper;
import com.example.fleettrack.model.CarStatus;
import com.example.fleettrack.model.VehicleSpecification;
import com.example.fleettrack.model.request.PagesRequest;
import com.example.fleettrack.model.request.VehicleFilterRequest;
import com.example.fleettrack.model.request.VehicleRequest;
import com.example.fleettrack.model.response.VehicleFilterResponse;
import com.example.fleettrack.model.response.VehicleResponse;
import com.example.fleettrack.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleResponseMapper vehicleResponseMapper;
    private final VehicleFilterResponseMapper vehicleFilterResponseMapper;

    @CachePut(value = "vehicle",key = "#vehicleRequest.licensePlate()")
    public VehicleResponse createVehicle(VehicleRequest vehicleRequest) {
        if (vehicleRepository.existsByLicensePlate(vehicleRequest.licensePlate())) {
            throw new BusinessException(ExceptionConstants.VEHICLE_ALREADY_EXISTS);
        }
        Vehicle newVehicle=Vehicle.builder()
                .make(vehicleRequest.make())
                .model(vehicleRequest.model())
                .manufactureYear(vehicleRequest.manufactureYear())
                .licensePlate(vehicleRequest.licensePlate())
                .carStatus(CarStatus.AVAILABLE).build();
        return vehicleResponseMapper.maptoVehicleResponse( vehicleRepository.save(newVehicle));
    }
    @CachePut(value = "vehicle",key = "#result.licensePlate()")
    public VehicleResponse updateVehicle(Long vehicleId,VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.VEHICLE_NOT_FOUND));
        vehicleResponseMapper.uptadeVehicle(vehicleRequest,vehicle);
        return vehicleResponseMapper.maptoVehicleResponse(vehicleRepository.save(vehicle));
    }
    @CacheEvict(value = "vehicle",key = "#result.licensePlate()")
    public VehicleResponse deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.VEHICLE_NOT_FOUND));
        vehicleRepository.delete(vehicle);
        return vehicleResponseMapper.maptoVehicleResponse(vehicle);
    }
    @Cacheable(value = "vehicle", key = "#licensePlate")
    public VehicleResponse getVehicleByLicensePlate(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.VEHICLE_NOT_FOUND));
        return vehicleResponseMapper.maptoVehicleResponse(vehicle);
    }
    public VehicleFilterResponse getAllVehicles(VehicleFilterRequest vehicleFilterRequest, PagesRequest pagesRequest) {
        Specification<Vehicle>vehicleSpecification= VehicleSpecification.toSpecification(vehicleFilterRequest);
        Page<Vehicle>vehiclePage=vehicleRepository.findAll(vehicleSpecification,pagesRequest.toPageable());
        return vehicleFilterResponseMapper.maptoVehicleFilterResponse(vehiclePage);
    }
}
