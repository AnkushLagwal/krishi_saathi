package com.example.krishisaathi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.krishisaathi.Repository.FarmerRepository;
import com.example.krishisaathi.model.Farmers;

import java.util.List;

@Service
public class FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    // Fetch logged-in farmer
    public Farmers getFarmerByEmail(String email) {
        return farmerRepository.findByEmail(email);
    }

    // Fetch all registered farmers
    public List<Farmers> getAllFarmers() {
        return farmerRepository.findAll();
    }
}

