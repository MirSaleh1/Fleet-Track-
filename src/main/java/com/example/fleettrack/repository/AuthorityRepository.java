package com.example.fleettrack.repository;

import com.example.fleettrack.entity.Authority;
import com.example.fleettrack.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    Optional<Authority> findAllByType(RoleType type);

}

