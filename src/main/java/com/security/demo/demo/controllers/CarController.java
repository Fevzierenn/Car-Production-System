package com.security.demo.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import com.security.demo.demo.crudAPIs.CarCrudEntity;
import com.security.demo.demo.dtos.CarDto;
import com.security.demo.demo.entities.Car;
import com.security.demo.demo.exceptions.CarNotExistsException;
import com.security.demo.demo.exceptions.DuplicateCarException;
import com.security.demo.demo.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/cars")
@Slf4j
public class CarController implements CarCrudEntity  {

    CarService carService;
    public CarController(CarService carService){
        this.carService = carService;
    }


    @PostMapping
    public ResponseEntity addCar(@RequestBody CarDto carDto) {
        try 
        {
            Car car = carService.saveCarToDB(carDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(car);
        } 
        catch (DuplicateCarException ex) 
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

  
    @GetMapping("/{carId}")
    public ResponseEntity getCar(@PathVariable(name = "carId") Integer carId) {
        try
        {
        Car car = carService.getCarById(carId);
        log.info("Car found: " + car.toString());
        return ResponseEntity.status(HttpStatus.OK).body(car);
        
    }
        catch(CarNotExistsException cause){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
    }

   
    @PutMapping("/{carId}")
    public ResponseEntity updateCar(@PathVariable Integer carId, @RequestBody CarDto carDto) {
       try{
         Car updatedCar = carService.updateCarInfos(carId, carDto);
        return  ResponseEntity.status(HttpStatus.OK).body(updatedCar);
       }
        catch(CarNotExistsException cause)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The car doen not exists for an update.");
        }
    }

 
    @DeleteMapping("/{carId}")
    public ResponseEntity deleteCar(@PathVariable Integer carId) {
        try
        {
            carService.deleteCarById(carId);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        catch(CarNotExistsException cause)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        
    }


  

    
}
