package com.example.fleettrack.entity;

import com.example.fleettrack.model.CarStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private Integer manufactureYear;
    @Column(nullable = false, unique = true)
    private String licensePlate;
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

}
