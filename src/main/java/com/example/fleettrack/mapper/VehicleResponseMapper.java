package com.example.fleettrack.mapper;

import com.example.fleettrack.entity.Driver;
import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.model.request.DriverRequest;
import com.example.fleettrack.model.request.VehicleRequest;
import com.example.fleettrack.model.response.VehicleResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleResponseMapper {

    VehicleResponse maptoVehicleResponse(Vehicle vehicle);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void uptadeVehicle(VehicleRequest VehicleRequest, @MappingTarget Vehicle vehicle);
}
