package com.example.MoneyBridge;

import com.example.MoneyBridge.Models.CampusLocation;
import com.example.MoneyBridge.Models.User;
import com.example.MoneyBridge.repository.CampusLocationRepository;
import com.example.MoneyBridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CampusLocationRepository locationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(CampusLocationRepository locationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (locationRepository.findByName("Main Campus Gate").isEmpty()) {
            CampusLocation location = new CampusLocation();
            location.setName("Main Campus Gate");
            locationRepository.save(location);
            System.out.println("Created initial campus location.");
        }

        if (userRepository.findByPhoneNumber("9894755053").isEmpty()) {
            User admin = new User();
            admin.setFullName("sanjith");
            admin.setPhoneNumber("9894755053");
            admin.setPasswordHash(passwordEncoder.encode("27399"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("Created default admin user: sanjith");
        }
    }
}