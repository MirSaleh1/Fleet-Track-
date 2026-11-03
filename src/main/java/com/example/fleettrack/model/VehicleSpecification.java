package com.example.fleettrack.model;


import com.example.fleettrack.entity.Vehicle;
import com.example.fleettrack.model.request.VehicleFilterRequest;
import com.example.fleettrack.model.request.VehicleRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface VehicleSpecification {
    static Specification<Vehicle> toSpecification(VehicleFilterRequest vehicleFilterRequest) {
        return (root, query, cb) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (vehicleFilterRequest.make() != null && !vehicleFilterRequest.make().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("make")), "%" + vehicleFilterRequest.make().toLowerCase() + "%"));
            }
            if (vehicleFilterRequest.model() != null && !vehicleFilterRequest.model().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("model")), "%" + vehicleFilterRequest.model().toLowerCase() + "%"));
            }
            if (vehicleFilterRequest.manufactureYear() != null) {
                predicates.add(cb.equal(root.get("manufactureYear"), vehicleFilterRequest.manufactureYear()));
            }
            if (vehicleFilterRequest.licensePlate() != null && !vehicleFilterRequest.licensePlate().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("licensePlate")), "%" + vehicleFilterRequest.licensePlate().toLowerCase() + "%"));
            }
            if (vehicleFilterRequest.carStatus() != null) {
            predicates.add(cb.equal(root.get("carStatus"), vehicleFilterRequest.carStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
