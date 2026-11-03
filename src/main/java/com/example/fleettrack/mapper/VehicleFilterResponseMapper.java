package com.example.fleettrack.mapper;

import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.model.response.VehicleFilterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",uses = VehicleResponseMapper.class)
public interface VehicleFilterResponseMapper {
    @Mapping(target = "vehicleResponses", source = "content")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "currentPage", source = "number")
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "hasNext", expression = "java(page.hasNext())")
    VehicleFilterResponse maptoVehicleFilterResponse(Page<Vehicle> page);
}
