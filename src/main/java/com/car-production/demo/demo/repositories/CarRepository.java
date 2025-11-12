package com.security.demo.demo.repositories;

import org.springframework.stereotype.Repository;

import com.security.demo.demo.entities.Car;
import com.security.demo.demo.enums.CarType;
import com.security.demo.demo.enums.FuelType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT COUNT(c) = 0 FROM Car c WHERE c.carType = ?1 AND c.model = ?2 AND c.perform = ?3 AND c.fuelType = ?4")
    boolean countHowManyObject(CarType carType, String model, String perform, FuelType fuelType);
	

    // boolean existsByCarTypeAndModelAndPerformAndTypeOfFuel();
}
