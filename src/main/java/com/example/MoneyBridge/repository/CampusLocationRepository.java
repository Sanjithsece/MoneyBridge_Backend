package com.example.MoneyBridge.repository;

import com.example.MoneyBridge.Models.CampusLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // ADD THIS IMPORT

public interface CampusLocationRepository extends JpaRepository<CampusLocation, Integer> {
    Optional<CampusLocation> findByName(String name);
}