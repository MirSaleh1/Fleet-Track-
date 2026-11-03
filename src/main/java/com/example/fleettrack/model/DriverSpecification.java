package com.example.fleettrack.model;


import com.example.fleettrack.entity.Driver;
import com.example.fleettrack.model.request.DriverRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface DriverSpecification {
    static Specification<Driver> toSpecification(DriverRequest driverRequest) {
        return (root, query, cb) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if (driverRequest.firstName() != null && !driverRequest.firstName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + driverRequest.firstName().toLowerCase() + "%"));
            }
            if (driverRequest.lastName() != null && !driverRequest.lastName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + driverRequest.lastName().toLowerCase() + "%"));
            }
            if (driverRequest.licenseNumber() != null && !driverRequest.licenseNumber().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("licenseNumber")), "%" + driverRequest.licenseNumber().toLowerCase() + "%"));
            }
            if (driverRequest.phoneNumber() != null && !driverRequest.phoneNumber().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("phoneNumber")), "%" + driverRequest.phoneNumber().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
