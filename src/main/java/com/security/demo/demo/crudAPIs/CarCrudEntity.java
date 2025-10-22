package com.security.demo.demo.crudAPIs;

import org.springframework.http.ResponseEntity;

import com.security.demo.demo.dtos.CarDto;

public interface CarCrudEntity {
    
    public ResponseEntity addCar(CarDto car);     //if method private -> need body. 
   
    public ResponseEntity getCar(Integer carId);

    public ResponseEntity updateCar(Integer carId,CarDto car);

    public ResponseEntity deleteCar(Integer carId);

}
