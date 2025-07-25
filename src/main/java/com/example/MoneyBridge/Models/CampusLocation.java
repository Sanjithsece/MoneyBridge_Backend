package com.example.MoneyBridge.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "campus_locations")
@Data
public class CampusLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean isActive = true;
}