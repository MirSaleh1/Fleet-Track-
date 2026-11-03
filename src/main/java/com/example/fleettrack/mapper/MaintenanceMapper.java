package com.example.fleettrack.mapper;
import com.example.fleettrack.entity.Maintenance;
import com.example.fleettrack.model.response.MaintenanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface MaintenanceMapper {
    @Mapping(target = "vehicleId", source = "vehicle.id")
    MaintenanceResponse toResponse(Maintenance maintenance);
}