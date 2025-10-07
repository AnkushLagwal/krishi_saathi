package com.example.krishisaathi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.krishisaathi.model.Farmers;

public interface FarmerRepository extends JpaRepository<Farmers, Long> {
	Farmers findByMobileNo(String mobileNo);
	 // Find farmer by email (for logged-in profile)
    Farmers findByEmail(String email);


}
