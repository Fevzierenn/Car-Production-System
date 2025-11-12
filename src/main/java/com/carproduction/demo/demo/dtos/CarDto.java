package com.carproduction.demo.demo.dtos;

import com.carproduction.demo.demo.enums.CarType;
import com.carproduction.demo.demo.enums.FuelType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    
    CarType carType;
    String model;
    String perform;
    Double price;
    FuelType fuelType;
}
