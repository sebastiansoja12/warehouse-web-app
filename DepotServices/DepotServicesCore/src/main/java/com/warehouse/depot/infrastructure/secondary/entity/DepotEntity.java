package com.warehouse.depot.infrastructure.secondary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "depot.DepotEntity")
@Builder
@Table(name = "depot")
public class DepotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, unique = true)
    private String depotCode;

    @Column(nullable = false, unique = true)
    private double lat;

    @Column(nullable = false, unique = true)
    private double lon;

}