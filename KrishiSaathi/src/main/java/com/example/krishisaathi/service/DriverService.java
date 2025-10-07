package com.example.krishisaathi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.krishisaathi.Repository.DriversRepository;
import com.example.krishisaathi.model.Drivers;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriversRepository driversRepo;

    // ✅ Register driver
    public Drivers saveDriver(Drivers driver) {
        return driversRepo.save(driver);
    }

    // ✅ Fetch by mobile (for login)
    public Drivers getDriverByMobile(String mobile) {
        return driversRepo.findByMobile(mobile);
    }

    // ✅ Fetch all registered drivers
    public List<Drivers> getAllDrivers() {
        return driversRepo.findAll();
    }

    // ✅ Count total drivers
    public long countAllDrivers() {
        return driversRepo.count();
    }

    // ✅ Count unique vehicles
    public long countUniqueVehicles() {
        return driversRepo.findAll()
                .stream()
                .map(Drivers::getVehicleNo)
                .distinct()
                .count();
    }

    // ✅ Count unique districts (locations)
    public long countUniqueDistricts() {
        return driversRepo.findAll()
                .stream()
                .map(Drivers::getDistrict)
                .distinct()
                .count();
    }

    // ✅ Count available drivers
    public long countAvailableDrivers() {
        return driversRepo.findAll()
                .stream()
                .filter(driver -> "AVAILABLE".equalsIgnoreCase(driver.getState()))
                .count();
    }
}
