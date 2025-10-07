package com.example.krishisaathi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.krishisaathi.model.Drivers;

public interface DriversRepository extends JpaRepository<Drivers, Long> {

	Drivers findByMobile(String mobile);

}
