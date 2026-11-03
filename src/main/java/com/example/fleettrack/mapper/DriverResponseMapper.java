package com.example.fleettrack.mapper;

import com.example.fleettrack.entity.Driver;
import com.example.fleettrack.model.request.DriverRequest;
import com.example.fleettrack.model.response.DriverResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = VehicleResponseMapper.class)
public interface DriverResponseMapper {
    @Mapping(target = "vehicleResponseList", source = "assignedVehicles")
    DriverResponse maptoDriverResponse(Driver driver);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void uptadeDriver(DriverRequest driverRequest, @MappingTarget Driver driver);
}
