package com.example.fleettrack.mapper;

import com.example.fleettrack.entity.Driver;
import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.model.response.DriverFilterResponse;
import com.example.fleettrack.model.response.VehicleFilterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",uses =DriverResponseMapper.class)
public interface DriverFilterResponseMapper {
    @Mapping(target = "drivers", source = "content")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "currentPage", source = "number")
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "hasNext", expression = "java(page.hasNext())")
    DriverFilterResponse maptoVehicleFilterResponse(Page<Driver> page);
}
