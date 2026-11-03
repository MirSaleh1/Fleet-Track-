package com.example.fleettrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String licenseNumber;
    private String phoneNumber;
    @OneToMany(mappedBy = "driver",cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Vehicle>assignedVehicles = new ArrayList<>();

}
